package com.green.greengram4.feed;

import com.green.greengram4.common.Const;
import com.green.greengram4.common.MyFileUtils;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.exception.FeedErrorCode;
import com.green.greengram4.exception.RestApiException;
import com.green.greengram4.feed.model.*;
import com.green.greengram4.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper feedMapper;
    private final FeedPicsMapper picsMapper;
    private final FeedFavMapper favMapper;
    private final FeedCommentMapper commentMapper;
    private final AuthenticationFacade authenticationFacade;
    private final MyFileUtils myFileUtils;

    public FeedInsPicDto postFeed(List<MultipartFile> pics, FeedInsDto dto) {
        if (pics == null) {
            throw new RestApiException(FeedErrorCode.PICS_MORE_THEN_ONE);
        }
        dto.setIuser(authenticationFacade.getLoginUserPk());
        FeedInsProcDto pDto1 = FeedInsProcDto.builder()
                .iuser(dto.getIuser())
                .contents(dto.getContents())
                .location(dto.getLocation())
                .build();
        int feedResult = feedMapper.insFeed(pDto1);

        String target = "/feed/" + pDto1.getIfeed();
        List<String> savedPics = new ArrayList<>();
        for (MultipartFile file : pics) {
            String saveFileNm = myFileUtils.transferTo(file, target);
            savedPics.add(saveFileNm);
        }

        FeedInsPicDto pDto2 = FeedInsPicDto.builder()
                .ifeed(pDto1.getIfeed())
                .pics(savedPics)
                .build();
        int picsResult = picsMapper.insPic(pDto2);

        return pDto2;
    }

    public List<FeedSelVo> getAllFeed(FeedSelDto dto) {
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
