package com.example.common_lib.java_bean;


public class ConfigBean {

    public static final String STATUS_UN_ENABLED = "0";
    public static final String STATUS_ENABLED = "1";

    public static final String DIRECT_PUSH = "dy_wisdom.user.direct_push";//直接销售
    public static final String INDIRECT_PUSH = "dy_wisdom.user.indirect_push";//间接销售
    public static final String INDIRECT_AGENT = "dy_wisdom.user.indirect_agent";//代理商新增业绩
    public static final String INDIRECT_SERVICE = "dy_wisdom.user.indirect_service";//服务商新增业绩
    public static final String INDIRECT_SERVICE_CENTER = "dy_wisdom.user.indirect_service_center";//服务中心新增业绩
    public static final String SERVICE_BIG = "dy_wisdom.upgrade.service_big";//代理商升级服务商需要大区业绩大于20万
    public static final String SERVICE_SMALL = "dy_wisdom.upgrade.service_small";//代理商升级服务商需要小区业绩大于20万
    public static final String SERVICE_CENTER = "dy_wisdom.upgrade.service_center";//服务商升级服务中心需要小区业绩大于50万
    public static final String SERVICE_AGENT_NUM = "dy_wisdom.upgrade.service_agent_num";//代理商升级服务商需要底部有两个代理商
    public static final String PARTNER_FRANCHISEE_NUM = "dy_wisdom.upgrade.partner_franchisee_num";//加盟商升级合作商需要底部有两个加盟商
    public static final String AGENT_PARTNER_NUM = "dy_wisdom.upgrade.agent_partner_num";//合作商升级代理商需要底部有两个合作商
    public static final String SERVICE_CENTER_SERVICE_NUM = "dy_wisdom.upgrade.service_center_service_num";//服务商升级为服务中心需要底部有两个服务商


    private int config_id;
    private String config_name;
    private String config_value;
    private String config_status;
    private String config_describe;
    private String insert_time;
    private String update_time;



}
