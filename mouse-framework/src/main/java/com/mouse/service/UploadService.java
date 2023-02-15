package com.mouse.service;

import com.mouse.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 星星
 * @create 2023-02-14 8:36
 */
public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}
