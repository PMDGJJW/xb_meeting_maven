package com.service;

import com.constants.SysConstant;
import com.dao.ExcelDao;
import com.entity.User;
import com.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @auth jian j w
 * @date 2020/3/20 21:13
 * @Description
 */
public class ExcelService {
    //注入ExcelDao
    private ExcelDao excelDao = new ExcelDao();

    /*
     * @Description 导出为excel
     * @author jian j w
     * @date 2020/3/20
     */
        public Workbook dowLoadXml(String username,String minage,String maxage){
            Workbook wb = new HSSFWorkbook();
            //sheet名称
            Sheet sheet = wb.createSheet("用户信息列表");
            //创建sheet的第一行数据-即excel的表头
            Row headerRow = sheet.createRow(0);
            //循环创建表头
            for (int i = 0; i < SysConstant.headers.length; i++) {
                headerRow.createCell(i).setCellValue(SysConstant.headers[i]);
            }

            List<User> list = excelDao.dowLoadXml(username,minage,maxage);
            Row row;
            for (int i = 0; i < list.size(); i++) {

                //加一是因为表头的i值为0
                row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(list.get(i).getUsername() == null ? "" : list.get(i).getUsername());
                row.createCell(1).setCellValue(list.get(i).getRealName() == null ? "" : list.get(i).getRealName());
                String gender = "";
                if (list.get(i).getGender() != null||list.get(i).getGender() != "") {
                    if ("1".equals(list.get(i).getGender())) {
                        gender = "男";
                    } else if ("0".equals(list.get(i).getGender())) {
                        gender = "女";
                    }
                }
                row.createCell(2).setCellValue(gender);
                String age = list.get(i).getAge() == null ? "" : list.get(i).getAge().toString();
                row.createCell(3).setCellValue(age);

                String creattime = list.get(i).getCreateTime() ;
                if (!(creattime==null)){
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = null;
                    try {
                        date = df.parse(creattime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    creattime=simpleDateFormat.format(date);
                }
                row.createCell(4).setCellValue(creattime);
                row.createCell(5).setCellValue(list.get(i).getCreateBy() == null ? "" : list.get(i).getCreateBy());
            }
            return wb;
        }

    /*
     * @Description 下载用户上传模板
     * @author jian j w
     * @date 2020/3/20
     */
        public Workbook xmlTemplate(){
            Workbook wb = new HSSFWorkbook();
            //sheet名称
            Sheet sheet = wb.createSheet("用户信息提交上传模板");
            //创建sheet的第一行数据-即excel的表头
            Row headerRow = sheet.createRow(0);
            //循环创建表头
            for (int i = 0; i < SysConstant.headers.length; i++) {
                headerRow.createCell(i).setCellValue(SysConstant.headers[i]);
            }
            return wb;
        }

     /*
      * @Description 根据file与后缀名区分获取workbook实例
      * @author jian j w
      * @date 2020/3/20
      */
    public Workbook getWorkbook(InputStream inputStream, String suffix) throws Exception {
        Workbook wk = null;
        //后缀名结尾是xls的是2003版本workBook，后缀名是xlsx结尾的是2007版本workBook
        if ("xls".equalsIgnoreCase(suffix)) {
            wk = new HSSFWorkbook(inputStream);
        } else if ("xlsx".equalsIgnoreCase(suffix)) {
            wk = new XSSFWorkbook(inputStream);
        }
        return wk;
    }

    /*
     * @Description
     * @author jian j w
     * @date 2020/3/20
     */
    public List<User> getExcelData(Workbook wb) throws Exception {
        List<User> list = new ArrayList<>();
        Row row;
        User user;
        int numSheet = wb.getNumberOfSheets();
        if (numSheet > 0) {
            for (int i = 0; i < numSheet; i++) {
                //获取每个sheet
                Sheet sheet = wb.getSheetAt(i);
                //获取总行数
                int numRow = sheet.getLastRowNum();
                if (numRow > 0) {
                    for (int j = 1; j <= numRow; j++) {
                        //跳过excel sheet表格头部
                        row = sheet.getRow(j);
                        user = new User();
                        //读取每行的每个单元格数据，注入User中
                        // "用户名", "真实姓名", "性别", "年龄", "创建时间", "创建人"
                        user.setUsername(ExcelUtil.manageCell(row.getCell(0), null));
                        user.setRealName(ExcelUtil.manageCell(row.getCell(1), null));
                        String sex = ExcelUtil.manageCell(row.getCell(2), null);
                        user.setGender("男".equals(sex) ? "1" : "0");
                        user.setAge(Integer.valueOf(ExcelUtil.manageCell(row.getCell(3), null)));
                        user.setCreateBy("1");
                        user.setPassword("root");
                        list.add(user);
                    }
                }
            }
        }

        return list;
    }
}
