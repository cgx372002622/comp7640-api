package com.hkbu.comp7640.bean;

import lombok.Data;

@Data
public class Qiniu {

    private String accessKey;

    private String secretKey;

    private String bucket;

    private String resourcesUrl;

    private String zone;

}
