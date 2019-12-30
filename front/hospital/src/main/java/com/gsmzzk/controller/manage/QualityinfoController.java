package com.gsmzzk.controller.manage;

import com.gsmzzk.common.Const;
import com.gsmzzk.common.ResponseCode;
import com.gsmzzk.common.ServerResponse;
import com.gsmzzk.pojo.Baseinfo;
import com.gsmzzk.pojo.Qualityinfo;
import com.gsmzzk.pojo.UserInfo;
import com.gsmzzk.service.IQualityService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

@RestController
@RequestMapping("/quality")
public class QualityinfoController {

    @Autowired
    IQualityService qualityService;

    @RequestMapping(value = "/info")
    public ServerResponse findbypate(@RequestParam(value = "pageNum",defaultValue = "1",required = false) Integer pageNum,
                                     @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
                                     HttpSession session
                                     ){





















        UserInfo userInfo=(UserInfo)session.getAttribute(Const.USER);
        if(userInfo==null){
            return ServerResponse.createServerResponseByFail(ResponseCode.NEED_LOGIN,"需要登录");
        }
        return  qualityService.findByPage(pageNum, pageSize);
    }

    @RequestMapping(value = "/export")
    public void  export(@RequestParam("ids")String ids,
                                 HttpServletResponse response, HttpSession session) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String("baseinfo.xls".getBytes("utf-8"), "iso-8859-1"));

//        UserInfo userInfo=(UserInfo)session.getAttribute(Const.USER);
//        if(userInfo==null){
//            return ServerResponse.createServerResponseByFail(ResponseCode.NEED_LOGIN,"需要登录");
//        }

        ServerResponse<List<Qualityinfo>> serverResponse=qualityService.findInfobyids(ids);
        if(!serverResponse.isSucess()){
            return ;// ServerResponse.createServerResponseByFail(ResponseCode.ERROR,"查询下载数据失败");
        }
        List<Qualityinfo> users =serverResponse.getData();// userService.selectUsers();

        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet("质量信息");


        HSSFRow row = null;

        row = sheet.createRow(0);//创建第一个单元格
        row.setHeight((short) (26.25 * 20));
        HSSFCell hssfCell=row.createCell(0);
        hssfCell.setCellValue("医院基础信息");

        /*为标题设计空间
         * firstRow从第1行开始
         * lastRow从第0行结束
         *
         *从第1个单元格开始
         * 从第3个单元格结束
         */
        CellRangeAddress rowRegion = new CellRangeAddress(0, 0, 0, 124);
        sheet.addMergedRegion(rowRegion);

      /*CellRangeAddress columnRegion = new CellRangeAddress(1,4,0,0);
      sheet.addMergedRegion(columnRegion);*/


        /*
         * 动态获取数据库列 sql语句 select COLUMN_NAME from INFORMATION_SCHEMA.Columns where table_name='user' and table_schema='test'
         * 第一个table_name 表名字
         * 第二个table_name 数据库名称
         * */
        row = sheet.createRow(1);
        row.setHeight((short) (22.50 * 20));//设置行高
        row.createCell(0).setCellValue("医院编号");//为第一个单元格设值
        row.createCell(1).setCellValue("填表人姓名");//为第二个单元格设值
        row.createCell(2).setCellValue("单位名称");//为第三个单元格设值

        row.createCell(3).setCellValue("住院部手术室麻醉总数");//为第一个单元格设值
        row.createCell(4).setCellValue("按ASA分级(含急诊)统计麻醉总例次数");
        row.createCell(5).setCellValue("Ⅰ级");//为第二个单元格设值
        row.createCell(6).setCellValue("Ⅱ级");//为第三个单元格设值

        row.createCell(7).setCellValue("Ⅰ~Ⅱ级所占比例%");//为第一个单元格设值
        row.createCell(8).setCellValue("Ⅲ级");//为第二个单元格设值
        row.createCell(9).setCellValue("Ⅳ级");//为第三个单元格设值

        row.createCell(10).setCellValue("Ⅴ级");//为第一个单元格设值
        row.createCell(11).setCellValue("Ⅲ~Ⅴ级所占比例%");//为第二个单元格设值
        row.createCell(12).setCellValue("急诊手术-例次数");//为第三个单元格设值
        row.createCell(13).setCellValue("急诊手术-所占比例%");//为第一个单元格设值
        row.createCell(14).setCellValue("全身麻醉数-总数");//为第二个单元格设值
        row.createCell(15).setCellValue("全身麻醉数-吸入");//为第二个单元格设值
        row.createCell(16).setCellValue("全身麻醉数-静脉");//为第二个单元格设值
        row.createCell(17).setCellValue("全身麻醉数-复合");//为第二个单元格设值
        row.createCell(18).setCellValue("椎管内麻醉数-总数");//为第二个单元格设值
        row.createCell(19).setCellValue("椎管内麻醉数-腰麻");//为第二个单元格设值
        row.createCell(20).setCellValue("椎管内麻醉数-硬膜外");//为第二个单元格设值
        row.createCell(21).setCellValue("椎管内麻醉数-腰硬联合");//为第二个单元格设值
        row.createCell(22).setCellValue("神经干(丛)阻滞-总数");//为第二个单元格设值
        row.createCell(23).setCellValue("神经干(丛)阻滞-神经干");//为第二个单元格设值
        row.createCell(24).setCellValue("神经干(丛)阻滞-神经丛");//为第二个单元格设值

        row.createCell(25).setCellValue("其它-总数");//为第二个单元格设值
        row.createCell(26).setCellValue("其它-MAC");//为第二个单元格设值
        row.createCell(27).setCellValue("其它-其它");//为第二个单元格设值

        row.createCell(28).setCellValue("专科麻醉数-脑外科麻醉");//为第二个单元格设值
        row.createCell(29).setCellValue("专科麻醉数-胸外科麻醉");//为第二个单元格设值
        row.createCell(30).setCellValue("专科麻醉数-心血管外科麻醉");//为第二个单元格设值
        row.createCell(31).setCellValue("专科麻醉数-产科麻醉");//为第二个单元格设值
        row.createCell(32).setCellValue("专科麻醉数-小儿麻醉");//为第二个单元格设值
        row.createCell(33).setCellValue("专科麻醉数-老年麻醉");//为第二个单元格设值
        row.createCell(34).setCellValue("专科麻醉数-无痛内窥镜深度镇静、麻醉数(例次)");//为第二个单元格设值
        row.createCell(35).setCellValue("专科麻醉数-日间(门诊)手术室麻醉数(含介入手术治疗，次数)\n");//为第二个单元格设值
        row.createCell(36).setCellValue("无痛分娩及无痛人流数总数");//为第二个单元格设值

        row.createCell(37).setCellValue("有创或无创检查-胃肠道");//为第二个单元格设值
        row.createCell(38).setCellValue("有创或无创检查-呼吸系统");//为第二个单元格设值
        row.createCell(39).setCellValue("有创或无创检查-心血管");//为第二个单元格设值
        row.createCell(40).setCellValue("有创或无创检查-介入手术治疗");//为第二个单元格设值
        row.createCell(41).setCellValue("有创或无创检查-人流手术");//为第二个单元格设值
        row.createCell(42).setCellValue("有创或无创检查-分娩镇痛");//为第二个单元格设值
        row.createCell(43).setCellValue("有创或无创检查-其他");//为第二个单元格设值

        row.createCell(44).setCellValue("收住或诊治病人数-PACU");//为第二个单元格设值
        row.createCell(45).setCellValue("收住或诊治病人数-AICU");//为第二个单元格设值
        row.createCell(46).setCellValue("收住或诊治病人数-疼痛病房");//为第二个单元格设值


        row.createCell(47).setCellValue("神经干（丛）阻滞成功率-同期总数");//为第二个单元格设值
        row.createCell(48).setCellValue("神经干（丛）阻滞成功率-成功例数");//为第二个单元格设值
        row.createCell(49).setCellValue("神经干（丛）阻滞成功率-成功率%");//为第二个单元格设值

        row.createCell(50).setCellValue("硬膜外阻滞成功率-同期总数");//为第二个单元格设值
        row.createCell(51).setCellValue("硬膜外阻滞成功率-成功例数");//为第二个单元格设值
        row.createCell(52).setCellValue("硬膜外阻滞成功率-成功率%");//为第二个单元格设值


        row.createCell(53).setCellValue("困难气道处理成功率-同期总数");//为第二个单元格设值
        row.createCell(54).setCellValue("困难气道处理成功率-成功例数");//为第二个单元格设值
        row.createCell(55).setCellValue("困难气道处理成功率-成功率%");//为第二个单元格设值

        row.createCell(56).setCellValue("超声引导穿刺-同期总例数");//为第二个单元格设值
        row.createCell(57).setCellValue("超声引导穿刺-应用超声引导例数");//为第二个单元格设值
        row.createCell(58).setCellValue("超声引导穿刺-成功率%");//为第二个单元格设值


        row.createCell(59).setCellValue("麻醉开始后手术取消率-麻醉开始后手术取消例数数");//为第二个单元格设值
        row.createCell(60).setCellValue("麻醉开始后手术取消率-同期总数");//为第二个单元格设值
        row.createCell(61).setCellValue("麻醉开始后手术取消率-取消率%");//为第二个单元格设值


        row.createCell(62).setCellValue("非计划二次气管插管率-例数");//为第二个单元格设值
        row.createCell(63).setCellValue("非计划二次气管插管率-同期总数");//为第二个单元格设值
        row.createCell(64).setCellValue("非计划二次气管插管率%");//为第二个单元格设值

        row.createCell(65).setCellValue("PACU入室低体温例数");//为第二个单元格设值
        row.createCell(66).setCellValue("同期PACU入室测量体温患者总数");//为第二个单元格设值
        row.createCell(67).setCellValue("低体温率%");//为第二个单元格设值

        row.createCell(68).setCellValue("非计划转入ICU例数");//为第二个单元格设值
        row.createCell(69).setCellValue("非计划转入ICU率-同期总数");//为第二个单元格设值
        row.createCell(70).setCellValue("非计划转入ICU率%");//为第二个单元格设值


        row.createCell(71).setCellValue("插管拔管后声音嘶哑例数");//为第二个单元格设值
        row.createCell(72).setCellValue("全麻气管插管拔管后声音嘶哑发生率-同期总数");//为第二个单元格设值
        row.createCell(73).setCellValue("插管拔管后声音嘶哑率%");//为第二个单元格设值

        row.createCell(74).setCellValue("麻醉开始后24小时内死亡例数");//为第二个单元格设值
        row.createCell(75).setCellValue("同期麻醉总数");//为第二个单元格设值
        row.createCell(76).setCellValue("麻醉开始后24小时内死亡率%");//为第二个单元格设值


        row.createCell(77).setCellValue("麻醉开始后24小时内心跳骤停发生例数");//为第二个单元格设值
        row.createCell(78).setCellValue("麻醉开始后24小时内心跳骤停发生例数-同期麻醉总数");//为第二个单元格设值
        row.createCell(79).setCellValue("麻醉开始后24小时内心跳骤停发生率%");//为第二个单元格设值
        row.createCell(80).setCellValue("麻醉开始后24小时内心跳骤停抢救成功例数");//为第二个单元格设值
        row.createCell(81).setCellValue("麻醉开始后24小时内心跳骤停抢救成功率%");//为第二个单元格设值


        row.createCell(82).setCellValue("麻醉期间严重过敏反应发生例数");//为第二个单元格设值
        row.createCell(83).setCellValue("麻醉期间严重过敏反应发生例数-同期麻醉总数");//为第二个单元格设值
        row.createCell(84).setCellValue("麻醉期间严重过敏反应发生例数发生率%");//为第二个单元格设值
        row.createCell(85).setCellValue("麻醉期间严重过敏反应发生例数抢救成功例数");//为第二个单元格设值
        row.createCell(86).setCellValue("麻醉期间严重过敏反应发生例数救成功率%");//为第二个单元格设值


        row.createCell(87).setCellValue("椎管内麻醉后严重神经并发症发生例数");//为第二个单元格设值
        row.createCell(88).setCellValue("椎管内麻醉后严重神经并发症发生例数-同期麻醉总数");//为第二个单元格设值
        row.createCell(89).setCellValue("椎管内麻醉后严重神经并发症发生率%");//为第二个单元格设值


        row.createCell(90).setCellValue("深静脉穿刺严重并发症发生例数");//为第二个单元格设值
        row.createCell(91).setCellValue("深静脉穿刺严重同期操作总数");//为第二个单元格设值
        row.createCell(92).setCellValue("深静脉穿刺严重发生率%");//为第二个单元格设值


        row.createCell(93).setCellValue("入PACU超过3小时患者数");//为第二个单元格设值
        row.createCell(94).setCellValue("同期入PACU总数(无PACU请添同期麻醉总数)");//为第二个单元格设值
        row.createCell(95).setCellValue("麻醉后监测治疗室（PACU）转出延迟率发生率%");//为第二个单元格设值

        row.createCell(96).setCellValue("全麻术中知晓发生例数");//为第二个单元格设值
        row.createCell(97).setCellValue("全麻术中知晓同期全麻例数");//为第二个单元格设值
        row.createCell(98).setCellValue("全麻术中知晓发生率%");//为第二个单元格设值

        row.createCell(99).setCellValue("自体血回输例数");//为第二个单元格设值
        row.createCell(100).setCellValue("自体血回输总量ml");//为第二个单元格设值
        row.createCell(101).setCellValue("同期输血病人总数");//为第二个单元格设值
        row.createCell(102).setCellValue("回输率%");//为第二个单元格设值

        row.createCell(103).setCellValue("术后镇痛例数");//为第二个单元格设值
        row.createCell(104).setCellValue("术后镇痛同期麻醉总数");//为第二个单元格设值
        row.createCell(105).setCellValue("术后镇痛率%");//为第二个单元格设值
        row.createCell(106).setCellValue("麻醉后随访记录例数");//为第二个单元格设值
        row.createCell(107).setCellValue("麻醉后随访同期麻醉总数");//为第二个单元格设值
        row.createCell(108).setCellValue("麻醉后随访率%");//为第二个单元格设值



        row.createCell(109).setCellValue("手术后24小时心肌梗塞发生例数");//为第二个单元格设值
        row.createCell(110).setCellValue("手术后24小时心肌梗塞同期麻醉总数");//为第二个单元格设值
        row.createCell(111).setCellValue("手术后24小时心肌梗塞发生率%");//为第二个单元格设值

        row.createCell(112).setCellValue("手术后24小时新发昏迷发生例数");//为第二个单元格设值
        row.createCell(113).setCellValue("手术后24小时新发昏迷同期麻醉总数");//为第二个单元格设值
        row.createCell(114).setCellValue("手术后24小时新发昏迷发生率%");//为第二个单元格设值

        row.createCell(115).setCellValue("手术后24小时肺栓塞发生例数");//为第二个单元格设值
        row.createCell(116).setCellValue("手术后24小时肺栓塞同期麻醉总数");//为第二个单元格设值
        row.createCell(117).setCellValue("手术后24小时肺栓塞发生率%");//为第二个单元格设值

        row.createCell(118).setCellValue("手术后24小时心跳骤停发生例数");//为第二个单元格设值
        row.createCell(119).setCellValue("手术后24小时心跳骤停同期麻醉总数");//为第二个单元格设值
        row.createCell(120).setCellValue("手术后24小时心跳骤停发生率%");//为第二个单元格设值


        row.createCell(121).setCellValue("手术后24小时死亡发生例数");//为第二个单元格设值
        row.createCell(122).setCellValue("手术后24小时死亡同期麻醉总数");//为第二个单元格设值
        row.createCell(123).setCellValue("手术后24小时死亡发生率%");//为第二个单元格设值

        row.createCell(124).setCellValue("采集时间");




        for (int i = 0; i < users.size(); i++) {
            row = sheet.createRow(i + 2);

            Qualityinfo user = users.get(i);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getUsername());
            row.createCell(2).setCellValue(user.getUnitname());

            row.createCell(3).setCellValue(user.getWorkloadCount());
            row.createCell(4).setCellValue(user.getWorkloadRsaCount());
            row.createCell(5).setCellValue(user.getWorkloadRsa1());
            row.createCell(6).setCellValue(user.getWorkloadRsa2());
            row.createCell(7).setCellValue(user.getWorkloadRsa12Per());
            row.createCell(8).setCellValue(user.getWorkloadRsa3());
            row.createCell(9).setCellValue(user.getWorkloadRsa4());
            row.createCell(10).setCellValue(user.getWorkloadRsa5());
            row.createCell(11).setCellValue(user.getWorkloadRsa345Per());
            row.createCell(12).setCellValue(user.getWorkloadEmergencyCount());

            row.createCell(13).setCellValue(user.getWorkloadEmergencyPer());
            row.createCell(14).setCellValue(user.getWorkloadAnaCount());
            row.createCell(15).setCellValue(user.getWorkloadAnaEnter());
            row.createCell(16).setCellValue(user.getWorkloadAnaStatic());
            row.createCell(17).setCellValue(user.getWorkloadAnaComplex());
            row.createCell(18).setCellValue(user.getWorkloadCanCount());
            row.createCell(19).setCellValue(user.getWorkloadCanWaist());
            row.createCell(20).setCellValue(user.getWorkloadCanOut());
            row.createCell(21).setCellValue(user.getWorkloadCanUnit());
            row.createCell(22).setCellValue(user.getWorkloadNerveCount());

            row.createCell(23).setCellValue(user.getWorkloadNerve());
            row.createCell(24).setCellValue(user.getWorkloadNervePlexus());
            row.createCell(25).setCellValue(user.getWorkloadOtherCount());
            row.createCell(26).setCellValue(user.getWorkloadOtherMac());
            row.createCell(27).setCellValue(user.getWorkloadOtherOther());
            row.createCell(28).setCellValue(user.getWorkloadSpecialtyBrain());
            row.createCell(29).setCellValue(user.getWorkloadSpecialtyChest());
            row.createCell(30).setCellValue(user.getWorkloadSpecialtyCardiovascular());
            row.createCell(31).setCellValue(user.getWorkloadSpecialtyObstetrics());
            row.createCell(32).setCellValue(user.getWorkloadSpecialtyChild());

            row.createCell(33).setCellValue(user.getWorkloadSpecialtyOld());
            row.createCell(34).setCellValue(user.getWorkloadSpecialtyNopain());
            row.createCell(35).setCellValue(user.getWorkloadSpecialtyDay());
            row.createCell(36).setCellValue(user.getWorkloadPainlessCount());
            row.createCell(37).setCellValue(user.getWorkloadInvasiveTract());
            row.createCell(38).setCellValue(user.getWorkloadInvasiveBreathing());
            row.createCell(39).setCellValue(user.getWorkloadInvasiveHealth());
            row.createCell(40).setCellValue(user.getWorkloadInvasiveOperation());
            row.createCell(41).setCellValue(user.getWorkloadInvasiveStream());
            row.createCell(42).setCellValue(user.getWorkloadInvasiveChildbirth());

            row.createCell(43).setCellValue(user.getWorkloadInvasiveOther());
            row.createCell(44).setCellValue(user.getLivePacu());
            row.createCell(45).setCellValue(user.getLiveAicu());
            row.createCell(46).setCellValue(user.getLivePain());
            row.createCell(47).setCellValue(user.getLiveNerveTrunkCount());
            row.createCell(48).setCellValue(user.getLiveNerveTrunkSuccess());
            row.createCell(49).setCellValue(user.getLiveNerveTrunkPer());
            row.createCell(50).setCellValue(user.getLiveBlockCount());
            row.createCell(51).setCellValue(user.getLiveBlockSucess());
            row.createCell(52).setCellValue(user.getLiveBlockPer());

            row.createCell(53).setCellValue(user.getLiveDifficultyCount());
            row.createCell(54).setCellValue(user.getLiveDifficultySucess());
            row.createCell(55).setCellValue(user.getLiveDifficultyPer());
            row.createCell(56).setCellValue(user.getLiveUltrasonicCount());
            row.createCell(57).setCellValue(user.getLiveUltrasonicCount2());
            row.createCell(58).setCellValue(user.getLiveUltrasonicPer());
            row.createCell(59).setCellValue(user.getLiveAnaesthesiaCancelCount());
            row.createCell(60).setCellValue(user.getLiveAnaesthesiaCount());
            row.createCell(61).setCellValue(user.getLiveAnaesthesiaCancelPer());
            row.createCell(62).setCellValue(user.getLiveNoplainCount());

            row.createCell(63).setCellValue(user.getLiveNoplainTotalCount());
            row.createCell(64).setCellValue(user.getLiveNoplainPer());
            row.createCell(65).setCellValue(user.getLivePacuCount());
            row.createCell(66).setCellValue(user.getLivePacuTotalCount());
            row.createCell(67).setCellValue(user.getLivePacuPer());
            row.createCell(68).setCellValue(user.getLiveIcuCount());
            row.createCell(69).setCellValue(user.getLiveIcuTotalcount());
            row.createCell(70).setCellValue(user.getLiveIcuPer());
            row.createCell(71).setCellValue(user.getLiveGeneralCount());
            row.createCell(72).setCellValue(user.getLiveGeneralTotalcount());

            row.createCell(73).setCellValue(user.getLiveGeneralPer());
            row.createCell(74).setCellValue(user.getLiveDeadCount());
            row.createCell(75).setCellValue(user.getLiveDeadTotalcount());
            row.createCell(76).setCellValue(user.getLiveDeadPer());
            row.createCell(77).setCellValue(user.getLiveDeadSuccessCount());
            row.createCell(78).setCellValue(user.getLiveDeadSuccessMazuiCount());
            row.createCell(79).setCellValue(user.getLiveDeadSucessStartPer());
            row.createCell(80).setCellValue(user.getLiveDeadSucessTotalcount());
            row.createCell(81).setCellValue(user.getLiveDeadSucessPer());
            row.createCell(82).setCellValue(user.getLiveAllergySuccessCount());

            row.createCell(83).setCellValue(user.getLiveAllergySuccessMazuiCount());
            row.createCell(84).setCellValue(user.getLiveAllergySucessStartPer());
            row.createCell(85).setCellValue(user.getLiveAllergySucessTotalcount());
            row.createCell(86).setCellValue(user.getLiveAllergySucessPer());
            row.createCell(87).setCellValue(user.getLiveNeuralCount());
            row.createCell(88).setCellValue(user.getLiveNeuralTotalcount());
            row.createCell(89).setCellValue(user.getLiveNeuralPer());
            row.createCell(90).setCellValue(user.getLiveVeinCount());
            row.createCell(91).setCellValue(user.getLiveVeinTotalcount());
            row.createCell(92).setCellValue(user.getLiveVeinPer());

            row.createCell(93).setCellValue(user.getLiveNeuralPacuCount());
            row.createCell(94).setCellValue(user.getLiveNeuralPacuTotalcount());
            row.createCell(95).setCellValue(user.getLiveNeuralPacuStartPer());
            row.createCell(96).setCellValue(user.getLiveGeneralAnesthesiaCount());
            row.createCell(97).setCellValue(user.getLiveGeneralAnesthesiaTotalcount());
            row.createCell(98).setCellValue(user.getLiveGeneralAnesthesiaPer());
            row.createCell(99).setCellValue(user.getLiveBloodCount());
            row.createCell(100).setCellValue(user.getLiveBloodTotalcount());
            row.createCell(101).setCellValue(user.getLiveBloodPeopleCount());
            row.createCell(102).setCellValue(user.getLiveBloodPer());

            row.createCell(103).setCellValue(user.getLiveAnalgesiaCount());
            row.createCell(104).setCellValue(user.getLiveAnalgesiaTotalcount());
            row.createCell(105).setCellValue(user.getLiveAnalgesiaPer());
            row.createCell(106).setCellValue(user.getLiveFollowCount());
            row.createCell(107).setCellValue(user.getLiveFollowTotalcount());
            row.createCell(108).setCellValue(user.getLiveFollowPer());
            row.createCell(109).setCellValue(user.getLiveMyocardialCount());
            row.createCell(110).setCellValue(user.getLiveMyocardialTotalcount());
            row.createCell(111).setCellValue(user.getLiveMyocardialPer());
            row.createCell(112).setCellValue(user.getLiveComaCount());

            row.createCell(113).setCellValue(user.getLiveComaTotalcount());
            row.createCell(114).setCellValue(user.getLiveComaPer());
            row.createCell(115).setCellValue(user.getLiveLungCount());
            row.createCell(116).setCellValue(user.getLiveLungTotalcount());
            row.createCell(117).setCellValue(user.getLiveLungPer());
            row.createCell(118).setCellValue(user.getLiveHeartCount());
            row.createCell(119).setCellValue(user.getLiveHeartTotalcount());
            row.createCell(120).setCellValue(user.getLiveHeartPer());
            row.createCell(121).setCellValue(user.getLiveOperationDeadCount());
            row.createCell(122).setCellValue(user.getLiveOperationDeadTotalcount());

            row.createCell(123).setCellValue(user.getLiveOperationDeadPer());
            row.createCell(124).setCellValue(user.getCreateTime());












        }
        sheet.setDefaultRowHeight((short) (16.5 * 20));
        //列宽自适应
        for (int i = 0; i <= 13; i++) {
            sheet.autoSizeColumn(i);
        }


        OutputStream os = response.getOutputStream();
        wb.write(os);
        os.flush();
        os.close();


    }


}
