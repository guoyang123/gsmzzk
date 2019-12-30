package com.gsmzzk.pojo;

import java.util.Date;

public class Baseinfo {

    private int id  ;//           int  primary key  auto_increment,
    private String hospitalname ;//  varchar(100)   comment '医院名称',
    private String hospitaltype         ;// comment '医院类型',
    private String directorname   ;//varchar(50)    comment '科主任姓名',
    private String directorphone  ;//varchar(20)    comment '手机',
    private String directoremail  ;//varchar(50)    comment '邮箱',
    private String messagername   ;//varchar(50)     comment '信息员姓名',
    private String  messagerphone  ;//varchar(20)   comment '信息员手机号',
    private String  messageremail  ;//varchar(50)   comment '信息员邮箱',
    private String ot_1           ;//int   comment '住院部手术室使用台数',
    private String ot_2           ;//int  comment '日间(门诊)手术室使用台数',
    private String ot_3           ;//int  comment '介入治疗(需麻醉)台数',
    private String ot_4           ;//int  comment '内镜(无痛)检查台数',
    private String ot_5  ;//int  comment '人流及无痛分娩台数',
    private String nob_1 ;//varchar(50) comment '麻醉后监护室(PACU)',
    private String nob_2 ;//varchar(50) comment '麻醉科ICU(AICU)',
    private String nob_3 ;//varchar(50) comment '麻醉科疼痛病房',
    private String nob_4 ;//varchar(50) comment '日间手术麻醉后恢复室',
    private String ana_fixed_doctor ;//int comment '麻醉科固定在岗（本院）医师总数',
    private String ana_total_work ;//double comment '同期麻醉科完成麻醉总例次数（万例次）',
    private String ana_per ;//double comment '麻醉科医患比%',
    private String  ana_total_doctor ;//int comment '麻醉科医师总数',
    private String ana_aicu ;//varchar(50) comment 'AICU',
    private String ana_pain ;//varchar(50) comment '疼痛诊疗',
    private String ana_other ;//varchar(50) comment '其它',
    private String ana_total ;//int comment '总人数',
    private String ana_nurse_total ;//varchar(50) comment '麻醉科护士总人数临床麻醉（含PACU）',
    private String ana_nurse_aicu ;//varchar(50) comment 'aicu',
    private String ana_nurse_pain ;//varchar(50) comment '疼痛诊疗',
    private String ana_nurse_other ;//varchar(50) comment '其它',
    private String nurse_total ;//int comment '总人数'
    private Date create_time;
    private Date update_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHospitalname() {
        return hospitalname;
    }

    public void setHospitalname(String hospitalname) {
        this.hospitalname = hospitalname;
    }

    public String getHospitaltype() {
        return hospitaltype;
    }

    public void setHospitaltype(String hospitaltype) {
        this.hospitaltype = hospitaltype;
    }

    public String getDirectorname() {
        return directorname;
    }

    public void setDirectorname(String directorname) {
        this.directorname = directorname;
    }

    public String getDirectorphone() {
        return directorphone;
    }

    public void setDirectorphone(String directorphone) {
        this.directorphone = directorphone;
    }

    public String getDirectoremail() {
        return directoremail;
    }

    public void setDirectoremail(String directoremail) {
        this.directoremail = directoremail;
    }

    public String getMessagername() {
        return messagername;
    }

    public void setMessagername(String messagername) {
        this.messagername = messagername;
    }

    public String getMessagerphone() {
        return messagerphone;
    }

    public void setMessagerphone(String messagerphone) {
        this.messagerphone = messagerphone;
    }

    public String getMessageremail() {
        return messageremail;
    }

    public void setMessageremail(String messageremail) {
        this.messageremail = messageremail;
    }

    public String getOt_1() {
        return ot_1;
    }

    public void setOt_1(String ot_1) {
        this.ot_1 = ot_1;
    }

    public String getOt_2() {
        return ot_2;
    }

    public void setOt_2(String ot_2) {
        this.ot_2 = ot_2;
    }

    public String getOt_3() {
        return ot_3;
    }

    public void setOt_3(String ot_3) {
        this.ot_3 = ot_3;
    }

    public String getOt_4() {
        return ot_4;
    }

    public void setOt_4(String ot_4) {
        this.ot_4 = ot_4;
    }

    public String getOt_5() {
        return ot_5;
    }

    public void setOt_5(String ot_5) {
        this.ot_5 = ot_5;
    }

    public String getNob_1() {
        return nob_1;
    }

    public void setNob_1(String nob_1) {
        this.nob_1 = nob_1;
    }

    public String getNob_2() {
        return nob_2;
    }

    public void setNob_2(String nob_2) {
        this.nob_2 = nob_2;
    }

    public String getNob_3() {
        return nob_3;
    }

    public void setNob_3(String nob_3) {
        this.nob_3 = nob_3;
    }

    public String getNob_4() {
        return nob_4;
    }

    public void setNob_4(String nob_4) {
        this.nob_4 = nob_4;
    }

    public String getAna_fixed_doctor() {
        return ana_fixed_doctor;
    }

    public void setAna_fixed_doctor(String ana_fixed_doctor) {
        this.ana_fixed_doctor = ana_fixed_doctor;
    }

    public String getAna_total_work() {
        return ana_total_work;
    }

    public void setAna_total_work(String ana_total_work) {
        this.ana_total_work = ana_total_work;
    }

    public String getAna_per() {
        return ana_per;
    }

    public void setAna_per(String ana_per) {
        this.ana_per = ana_per;
    }

    public String getAna_total_doctor() {
        return ana_total_doctor;
    }

    public void setAna_total_doctor(String ana_total_doctor) {
        this.ana_total_doctor = ana_total_doctor;
    }

    public String getAna_aicu() {
        return ana_aicu;
    }

    public void setAna_aicu(String ana_aicu) {
        this.ana_aicu = ana_aicu;
    }

    public String getAna_pain() {
        return ana_pain;
    }

    public void setAna_pain(String ana_pain) {
        this.ana_pain = ana_pain;
    }

    public String getAna_other() {
        return ana_other;
    }

    public void setAna_other(String ana_other) {
        this.ana_other = ana_other;
    }

    public String getAna_total() {
        return ana_total;
    }

    public void setAna_total(String ana_total) {
        this.ana_total = ana_total;
    }

    public String getAna_nurse_total() {
        return ana_nurse_total;
    }

    public void setAna_nurse_total(String ana_nurse_total) {
        this.ana_nurse_total = ana_nurse_total;
    }

    public String getAna_nurse_aicu() {
        return ana_nurse_aicu;
    }

    public void setAna_nurse_aicu(String ana_nurse_aicu) {
        this.ana_nurse_aicu = ana_nurse_aicu;
    }

    public String getAna_nurse_pain() {
        return ana_nurse_pain;
    }

    public void setAna_nurse_pain(String ana_nurse_pain) {
        this.ana_nurse_pain = ana_nurse_pain;
    }

    public String getAna_nurse_other() {
        return ana_nurse_other;
    }

    public void setAna_nurse_other(String ana_nurse_other) {
        this.ana_nurse_other = ana_nurse_other;
    }

    public String getNurse_total() {
        return nurse_total;
    }

    public void setNurse_total(String nurse_total) {
        this.nurse_total = nurse_total;
    }


    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "Baseinfo{" +
                "id=" + id +
                ", hospitalname='" + hospitalname + '\'' +
                ", hospitaltype=" + hospitaltype +
                ", directorname='" + directorname + '\'' +
                ", directorphone='" + directorphone + '\'' +
                ", directoremail='" + directoremail + '\'' +
                ", messagername='" + messagername + '\'' +
                ", messagerphone='" + messagerphone + '\'' +
                ", messageremail='" + messageremail + '\'' +
                ", ot_1=" + ot_1 +
                ", ot_2=" + ot_2 +
                ", ot_3=" + ot_3 +
                ", ot_4=" + ot_4 +
                ", ot_5=" + ot_5 +
                ", nob_1='" + nob_1 + '\'' +
                ", nob_2='" + nob_2 + '\'' +
                ", nob_3='" + nob_3 + '\'' +
                ", nob_4='" + nob_4 + '\'' +
                ", ana_fixed_doctor=" + ana_fixed_doctor +
                ", ana_total_work=" + ana_total_work +
                ", ana_per=" + ana_per +
                ", ana_total_doctor=" + ana_total_doctor +
                ", ana_aicu='" + ana_aicu + '\'' +
                ", ana_pain='" + ana_pain + '\'' +
                ", ana_other='" + ana_other + '\'' +
                ", ana_total=" + ana_total +
                ", ana_nurse_total='" + ana_nurse_total + '\'' +
                ", ana_nurse_aicu='" + ana_nurse_aicu + '\'' +
                ", ana_nurse_pain='" + ana_nurse_pain + '\'' +
                ", ana_nurse_other='" + ana_nurse_other + '\'' +
                ", nurse_total=" + nurse_total +
                '}';
    }
}
