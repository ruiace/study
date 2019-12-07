package com.rp.service;

import com.rp.common.Constants;
import com.rp.common.EmptyUtils;
import com.rp.mapper.DmImageMapper;
import com.rp.pojo.DmImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class RestDmImageService {

    @Autowired
    private DmImageMapper dmImageMapper;

    @RequestMapping(value = "/getDmImageById", method = RequestMethod.POST)
    public DmImage getDmImageById(@RequestParam("id") Long id) throws Exception {
        return dmImageMapper.getDmImageById(id);
    }

    @RequestMapping(value = "/getDmImageListByMap", method = RequestMethod.POST)
    public List<DmImage> getDmImageListByMap(@RequestParam Map<String, Object> param) throws Exception {
        return dmImageMapper.getDmImageListByMap(param);
    }

    @RequestMapping(value = "/getDmImageCountByMap", method = RequestMethod.POST)
    public Integer getDmImageCountByMap(@RequestParam Map<String, Object> param) throws Exception {
        return dmImageMapper.getDmImageCountByMap(param);
    }

    @RequestMapping(value = "/qdtxAddDmImage", method = RequestMethod.POST)
    public Integer qdtxAddDmImage(@RequestBody DmImage dmImage) throws Exception {
        dmImage.setCreatedTime(new Date());
        return dmImageMapper.insertDmImage(dmImage);
    }

    @RequestMapping(value = "/qdtxModifyDmImage", method = RequestMethod.POST)
    public Integer qdtxModifyDmImage(@RequestBody DmImage dmImage) throws Exception {
        dmImage.setUpdatedTime(new Date());
        return dmImageMapper.updateDmImage(dmImage);
    }

    @RequestMapping(value = "/queryDmImageList", method = RequestMethod.POST)
    public List<DmImage> queryDmImageList(@RequestParam("targetId") Long targetId,
                                          @RequestParam("type") Integer type,
                                          @RequestParam("category") Integer category) throws Exception {
        Map<String, Object> imageParam = new HashMap<String, Object>();
        imageParam.put("targetId", targetId);
        imageParam.put("type", type);
        imageParam.put("category", category);
        List<DmImage> dmImageList = dmImageMapper.getDmImageListByMap(imageParam);
        return setDefaultImageList(dmImageList, category, type);
    }

    /**
     * 设置图片为空时默认显示图片
     *
     * @param dmImageList
     * @param category
     * @param type
     * @return
     */
    public List<DmImage> setDefaultImageList(List<DmImage> dmImageList, Integer category, Integer type) {
        if (EmptyUtils.isEmpty(dmImageList)) {
            dmImageList = new ArrayList<DmImage>();
            DmImage dmImage = new DmImage();
            dmImage.setType(type);
            dmImage.setCategory(category);
            dmImageList.add(dmImage);
        }
        for (DmImage dmImage : dmImageList) {
            if (EmptyUtils.isEmpty(dmImage.getImgUrl())) {
                dmImage.setImgUrl(Constants.DEFAULT_CAROUSEL);
            }
        }
        return dmImageList;
    }

}
