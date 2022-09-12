package com.sw.novel.service;

import com.sw.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

     R uploadImg(MultipartFile img);


}
