package com.example.common_lib.java_bean;


public class TeamInfoBean {

    private int business_num;
    private int coop_num;
    private int agent_num;//代理商数
    private int service_num;// 服务商数目
    private int service_center_num;// 服务商数目

    public int getBusiness_num() {
        return business_num;
    }

    public void setBusiness_num(int business_num) {
        this.business_num = business_num;
    }

    public int getCoop_num() {
        return coop_num;
    }

    public void setCoop_num(int coop_num) {
        this.coop_num = coop_num;
    }

    public int getAgent_num() {
        return agent_num;
    }

    public void setAgent_num(int agent_num) {
        this.agent_num = agent_num;
    }

    public int getService_num() {
        return service_num;
    }

    public void setService_num(int service_num) {
        this.service_num = service_num;
    }

    public int getService_center_num() {
        return service_center_num;
    }

    public void setService_center_num(int service_center_num) {
        this.service_center_num = service_center_num;
    }
}
