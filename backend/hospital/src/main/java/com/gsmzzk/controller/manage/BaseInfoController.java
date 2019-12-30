package com.gsmzzk.controller.manage;

import com.gsmzzk.common.Const;
import com.gsmzzk.common.ResponseCode;
import com.gsmzzk.common.ServerResponse;
import com.gsmzzk.pojo.Baseinfo;
import com.gsmzzk.pojo.UserInfo;
import com.gsmzzk.service.IBaseInfoService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

@RestController
@RequestMapping(value = "/base")
public class BaseInfoController {

    @Autowired
    private IBaseInfoService baseInfoService;
    @RequestMapping(value = "/info")
    public ServerResponse infos(@RequestParam(value = "pageNum",defaultValue = "1",required = false) Integer pageNum,
                                @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
                                HttpSession session){



       UserInfo userInfo=(UserInfo)session.getAttribute(Const.USER);
       if(userInfo==null){
           return ServerResponse.createServerResponseByFail(ResponseCode.NEED_LOGIN,"需要登录");
       }

        return baseInfoService.getBaseInfos(pageNum, pageSize);
    }


    @RequestMapping(value = "/export")
    public void export(@RequestParam("ids")String ids, HttpServletResponse response,HttpSession session) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String("baseinfo.xls".getBytes("utf-8"), "iso-8859-1"));

//        UserInfo userInfo=(UserInfo)session.getAttribute(Const.USER);
//        if(userInfo==null){
//            return ServerResponse.createServerResponseByFail(ResponseCode.NEED_LOGIN,"需要登录");
//        }

        ServerResponse<List<Baseinfo>> serverResponse=baseInfoService.findInfobyids(ids);
        if(!serverResponse.isSucess()){
            return ;//ServerResponse.createServerResponseByFail(ResponseCode.ERROR,"查询下载数据失败");
        }
        List<Baseinfo> users =serverResponse.getData();// userService.selectUsers();

        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet("基础信息");


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
        CellRangeAddress rowRegion = new CellRangeAddress(0, 0, 0, 31);
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
        row.createCell(1).setCellValue("医院名称");//为第二个单元格设值
        row.createCell(2).setCellValue("医院类型");//为第三个单元格设值

        row.createCell(3).setCellValue("科主任姓名");//为第一个单元格设值
        row.createCell(4).setCellValue("手机");//为第二个单元格设值
        row.createCell(5).setCellValue("邮箱");//为第三个单元格设值

        row.createCell(6).setCellValue("信息员姓名");//为第一个单元格设值
        row.createCell(7).setCellValue("信息员手机");//为第二个单元格设值
        row.createCell(8).setCellValue("信息员邮箱");//为第三个单元格设值

        row.createCell(9).setCellValue("住院部手术室使用台数");//为第一个单元格设值
        row.createCell(10).setCellValue("日间(门诊)手术室使用台数");//为第二个单元格设值
        row.createCell(11).setCellValue("介入治疗(需麻醉)台数");//为第三个单元格设值
         row.createCell(12).setCellValue("内镜(无痛)检查台数");//为第一个单元格设值
        row.createCell(13).setCellValue("人流及无痛分娩台数");//为第二个单元格设值
        row.createCell(14).setCellValue("麻醉后监护室(PACU)");//为第三个单元格设值

        row.createCell(15).setCellValue("麻醉科ICU(AICU)");//为第一个单元格设值
        row.createCell(16).setCellValue("麻醉科疼痛病房");//为第二个单元格设值
        row.createCell(17).setCellValue("日间手术麻醉后恢复室");//为第三个单元格设值
        row.createCell(18).setCellValue("麻醉科固定在岗（本院）医师总数");//为第一个单元格设值
        row.createCell(19).setCellValue("同期麻醉科完成麻醉总例次数（万例次）");//为第二个单元格设值
        row.createCell(20).setCellValue("麻醉科医患比%");//为第三个单元格设值

        row.createCell(21).setCellValue("麻醉科医师总数");//为第一个单元格设值
        row.createCell(22).setCellValue("AICU");//为第二个单元格设值
        row.createCell(23).setCellValue("疼痛诊疗");//为第三个单元格设值
        row.createCell(24).setCellValue("其它");//为第一个单元格设值
        row.createCell(25).setCellValue("总人数");//为第二个单元格设值
        row.createCell(26).setCellValue("麻醉科护士总人数临床麻醉（含PACU）");//为第三个单元格设值


        row.createCell(27).setCellValue("aicu");//为第三个单元格设值
        row.createCell(28).setCellValue("疼痛诊疗");//为第一个单元格设值
        row.createCell(29).setCellValue("其它");//为第二个单元格设值
        row.createCell(30).setCellValue("总人数");//为第三个单元格设值
        row.createCell(31).setCellValue("采集时间");


        for (int i = 0; i < users.size(); i++) {
            row = sheet.createRow(i + 2);

            Baseinfo user = users.get(i);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getHospitalname());
            row.createCell(2).setCellValue(user.getHospitaltype());

            row.createCell(3).setCellValue(user.getDirectorname());
            row.createCell(4).setCellValue(user.getDirectorphone());
            row.createCell(5).setCellValue(user.getDirectoremail());
            row.createCell(6).setCellValue(user.getMessagername());
            row.createCell(7).setCellValue(user.getMessagerphone());
            row.createCell(8).setCellValue(user.getMessageremail());
            row.createCell(9).setCellValue(user.getOt_1());
            row.createCell(10).setCellValue(user.getOt_2());
            row.createCell(11).setCellValue(user.getOt_3());
            row.createCell(12).setCellValue(user.getOt_4());
            row.createCell(13).setCellValue(user.getOt_5());
            row.createCell(14).setCellValue(user.getNob_1());
            row.createCell(15).setCellValue(user.getNob_2());
            row.createCell(16).setCellValue(user.getNob_3());
            row.createCell(17).setCellValue(user.getNob_4());
            row.createCell(18).setCellValue(user.getAna_fixed_doctor());
            row.createCell(19).setCellValue(user.getAna_total_work());
            row.createCell(20).setCellValue(user.getAna_per());
            row.createCell(21).setCellValue(user.getAna_total_doctor());
            row.createCell(22).setCellValue(user.getAna_aicu());
            row.createCell(23).setCellValue(user.getAna_pain());
            row.createCell(24).setCellValue(user.getAna_other());
            row.createCell(25).setCellValue(user.getAna_total());
            row.createCell(26).setCellValue(user.getAna_nurse_total());
            row.createCell(27).setCellValue(user.getAna_nurse_aicu());
            row.createCell(28).setCellValue(user.getAna_nurse_pain());
            row.createCell(29).setCellValue(user.getAna_nurse_other());
            row.createCell(30).setCellValue(user.getNurse_total());
            row.createCell(31).setCellValue(user.getCreate_time());





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
