package com.meetingfilm.film.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.meetingfilm.apis.films.vo.FilmsInfoRespVO;
import com.meetingfilm.film.controller.vo.ActorsListRespVO;
import com.meetingfilm.film.controller.vo.FilmsAddReqVO;
import com.meetingfilm.film.controller.vo.FilmsListRespVO;
import com.meetingfilm.film.service.IFilmService;
import com.meetingfilm.utils.common.vo.BasePageVO;
import com.meetingfilm.utils.common.vo.BaseResponseVo;
import com.meetingfilm.utils.exception.CommonServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 影片模块
 * </p>
 *
 * @author xqc
 * @since 2020-12-09
 */
@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    @Autowired
    private IFilmService filmService;

    /**
     * 影片列表
     *
     * @return
     */
    @RequestMapping
    public BaseResponseVo filmsList(BasePageVO pageVO) throws CommonServiceException {
        //校验
        pageVO.checkParam();
        //服务层
        IPage<FilmsListRespVO> respVO = filmService.getFilmsList(pageVO);
        return BaseResponseVo.success(pageResult(respVO, "films"));
    }

    /**
     * 影片详情
     *
     * @return
     */
    @RequestMapping("/{filmId}")
    public BaseResponseVo filmsInfo(@PathVariable("filmId") String filmId, HttpServletRequest request) throws CommonServiceException {
        //请求头内容
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String head = headerNames.nextElement();
            log.info("films headName:{},value:{}", head, request.getHeader(head));
        }
        FilmsInfoRespVO filmInfo = filmService.getFilmInfo(filmId);
        if (filmInfo == null) {
            throw new CommonServiceException(404, "未找到影片");
        }
        return BaseResponseVo.success(filmInfo);
    }

    /**
     * 影片详情
     *
     * @return
     */
    @RequestMapping("/add")
    public BaseResponseVo filmsAdd(@RequestBody FilmsAddReqVO reqVO) throws CommonServiceException {
        filmService.saveFilm(reqVO);
        return BaseResponseVo.success();
    }


    /**
     * 演员列表
     *
     * @return
     */
    @RequestMapping("/actors")
    public BaseResponseVo actorsList(BasePageVO pageVO) throws CommonServiceException {
        //校验
        pageVO.checkParam();
        //服务层
        IPage<ActorsListRespVO> respVO = filmService.getActorsList(pageVO);
        return BaseResponseVo.success(pageResult(respVO, "actors"));
    }

    /**
     * 分页返回值
     *
     * @param datas
     * @param title
     * @return
     */
    private Map<String, Object> pageResult(IPage page, String title) {
        Map<String, Object> result = new HashMap<>();
        result.put("totalSize", page.getTotal());
        result.put("totalPage", page.getPages());
        result.put("nowPage", page.getCurrent());
        result.put("pageSize", page.getSize());
        result.put(title, page.getRecords());
        return result;
    }
}
