package com.green.greengram4.feed;

import com.green.greengram4.common.Const;
import com.green.greengram4.common.MyFileUtils;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.entity.*;
import com.green.greengram4.exception.FeedErrorCode;
import com.green.greengram4.exception.RestApiException;
import com.green.greengram4.feed.model.*;
import com.green.greengram4.security.AuthenticationFacade;
import com.green.greengram4.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper feedMapper;
    private final FeedPicsMapper picsMapper;
    private final FeedFavMapper favMapper;
    private final FeedCommentMapper commentMapper;

    private final UserRepository userRepository;
    private final FeedRepository feedRepository;
    private final FeedCommentRepository commentRepository;
    private final FeedFavRepository favRepository;

    private final AuthenticationFacade authenticationFacade;
    private final MyFileUtils myFileUtils;

    @Transactional
    public FeedInsPicDto postFeed(List<MultipartFile> pics, FeedInsDto dto) {
        if (pics == null) {
            throw new RestApiException(FeedErrorCode.PICS_MORE_THEN_ONE);
        }

        UserEntity userEntity = userRepository.getReferenceById((long) authenticationFacade.getLoginUserPk());
//        UserEntity userEntity = new UserEntity();
//        userEntity.setIuser((long)authenticationFacade.getLoginUserPk()); 비영속 상태로도 작동 한다.
        FeedEntity feedEntity = new FeedEntity();
        feedEntity.setUserEntity(userEntity);
        feedEntity.setContents(dto.getContents());
        feedEntity.setLocation(dto.getLocation());
        feedRepository.save(feedEntity);

        String target = "/feed/" + feedEntity.getIfeed();

        FeedInsPicDto pDto = FeedInsPicDto.builder()
                .ifeed(feedEntity.getIfeed().intValue())
                .pics(new ArrayList<>())
                .build();
        for (MultipartFile file : pics) {
            String saveFileNm = myFileUtils.transferTo(file, target);
            pDto.getPics().add(saveFileNm);
        }
        List<FeedPicsEntity> feedPicsEntityList = pDto.getPics()
                .stream()
                .map(item -> FeedPicsEntity.builder()
                        .feedEntity(feedEntity)
                        .pic(item)
                        .build()
                ).toList();
        feedEntity.getFeedPicsEntityList().addAll(feedPicsEntityList);

        return pDto;

    }

    @Transactional
    public List<FeedSelVo> getAllFeed(FeedSelDto dto, Pageable pageable) {
        dto.setLoginedIuser(authenticationFacade.getLoginUserPk());
        List<FeedEntity> list = feedRepository.selFeedAll(dto, pageable);

        List<FeedPicsEntity> picList = feedRepository.selFeedPicsAll(list);
        List<FeedFavEntity> favList = dto.getIsFavList() == 1 ? null : feedRepository.selFeedMyFavAll(list, dto.getLoginedIuser());
        List<FeedSelCommentVo> commentList = commentMapper.selFeedCommentTop4(list);
        return list.stream().map(item -> {
                    List<FeedSelCommentVo> eachCommentList = commentList.stream().filter(comment -> comment.getIfeed() == item.getIfeed()).toList();

                    return FeedSelVo.builder()
                            .ifeed(item.getIfeed().intValue())
                            .location(item.getLocation())
                            .contents(item.getContents())
                            .createdAt(item.getCreatedAt().toString())
                            .writerIuser(item.getUserEntity().getIuser().intValue())
                            .writerNm(item.getUserEntity().getNm())
                            .writerPic(item.getUserEntity().getPic())
                            .pics(picList.stream().filter(pic ->
                                            pic.getFeedEntity().getIfeed().equals(item.getIfeed()))
                                    .map(FeedPicsEntity::getPic)
                                    .collect(Collectors.toList())
                            )
                            .isFav(dto.getIsFavList() == 1 ?
                                    1
                                    : favList.stream().anyMatch(fav -> fav.getFeedEntity().getIfeed() == item.getIfeed()) ?
                                    1
                                    : 0)
                            .build();
                }
        ).toList();
    }

    @Transactional
    public List<FeedSelVo> getAllFeed3(FeedSelDto dto, Pageable pageable) {
        List<FeedEntity> list = null;
        if (dto.getIsFavList() == 0 && dto.getTargetIuser() > 0) {
            UserEntity userEntity = new UserEntity();
            userEntity.setIuser((long) dto.getTargetIuser());
            list = feedRepository.findAllByUserEntityOrderByIfeedDesc(userEntity, pageable);
        }
        return list == null ?
                new ArrayList<>() :
                list.stream().map(item -> {
                    List<FeedCommentEntity> commentEntityList = commentRepository.findAllTop4ByFeedEntity(item);
                    List<FeedSelCommentVo> commentsList = commentEntityList.stream()
                            .map(cmt -> FeedSelCommentVo.builder()
                                    .ifeedComment(cmt.getIfeedComment().intValue())
                                    .comment(cmt.getComment())
                                    .createdAt(cmt.getCreatedAt().toString())
                                    .writerIuser(cmt.getUserEntity().getIuser().intValue())
                                    .writerNm(cmt.getUserEntity().getNm())
                                    .writerPic(cmt.getUserEntity().getPic())
                                    .build()).toList();
                    int isMoreComment = 0;
                    if (commentsList.size() == 4) {
                        isMoreComment++;
                        commentsList = new ArrayList<>(commentsList);
                        commentsList.remove(commentsList.size() - 1);
                    }

                    List<FeedPicsEntity> picsList = item.getFeedPicsEntityList();
                    List<String> pics = picsList.stream().map(FeedPicsEntity::getPic).toList();

                    FeedFavIds feedFavIds = new FeedFavIds();
                    feedFavIds.setIuser((long) authenticationFacade.getLoginUserPk());
                    feedFavIds.setIfeed(item.getIfeed());
                    int isFav = favRepository.findById(feedFavIds).isPresent() ? 1 : 0;

                    return FeedSelVo.builder()
                            .ifeed(item.getIfeed().intValue())
                            .contents(item.getContents())
                            .location(item.getLocation())
                            .createdAt(item.getCreatedAt().toString())
                            .writerIuser(item.getUserEntity().getIuser().intValue())
                            .writerNm(item.getUserEntity().getNm())
                            .writerPic(item.getUserEntity().getPic())
                            .comments(commentsList)
                            .isMoreComment(isMoreComment)
                            .pics(pics)
                            .isFav(isFav)
                            .build();
                }).toList();
    }

    public List<FeedSelVo> getAllFeed2(FeedSelDto dto) {
        List<FeedSelVo> resultVo = feedMapper.selAllFeed(dto);
        Map<Integer, FeedSelVo> map = new HashMap<>();
        //Map<Integer, FeedSelVo> map = resultVo.stream().collect(Collectors.toMap(FeedSelVo::getIfeed, FeedSelVo::getSelf));
        //List<Integer> ifeeds = new ArrayList<>();
        List<Integer> ifeeds = resultVo.stream().map(FeedSelVo::getIfeed).toList();
        FeedSelCommentDto commentDto = FeedSelCommentDto.builder()
                .startIdx(0)
                .commentCnt(Const.MAX_COMMENT_COUNT)
                .build();
        for (FeedSelVo vo : resultVo) {
            //ifeeds.add(vo.getIfeed());
            map.put(vo.getIfeed(), vo);

            commentDto.setIfeed(vo.getIfeed());
            List<FeedSelCommentVo> commentVoList = commentMapper.selFeedCommentAll(commentDto);
            vo.setComments(commentVoList);
            if (commentVoList.size() == Const.MAX_COMMENT_COUNT) {
                vo.setIsMoreComment(1);
                vo.getComments().remove(commentVoList.size() - 1);
            }
        }
        List<FeedSelPicVo> picsVo = picsMapper.selPicsByIfeeds(ifeeds);
        for (FeedSelPicVo picVo : picsVo) {
            map.get(picVo.getIfeed()).getPics().add(picVo.getPic());
        }
        return resultVo;
    }

    //좋아요 취소 - 0, 등록 - 1
    public ResVo toggleFeedFav(FeedToggleFavDto dto) {
        try {
            int delResult = favMapper.delFeedFav(dto);
            if (delResult == 1) {
                return new ResVo(Const.FAV_OFF);
            }
            int insResult = favMapper.insFeedFav(dto);
            return new ResVo(Const.FAV_ON);
        } catch (Exception e) {
            return new ResVo(Const.FAV_OFF);
        }
    }

    public ResVo delFeed(FeedDelDto dto) {
        int delProcResult = feedMapper.delFeedProc(dto);
        int delResult = feedMapper.delFeed(dto);
        return new ResVo(delResult);
    }
}
