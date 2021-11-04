package com.strongshop.mobile.service;

import com.amazonaws.services.s3.model.S3Object;

public interface DownloadService {

     S3Object downloadFile(String filename);

}
