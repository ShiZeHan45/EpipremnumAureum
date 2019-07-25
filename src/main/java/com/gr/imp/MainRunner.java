package com.gr.imp;


import com.gr.in.*;

/**
 * @program: wwis-kunming
 * @description:
 * @author: Shizh
 * @create: 2018-07-23 16:37
 **/
public class MainRunner {
    public void generateCode(){
//1.生成Bean实体类
        BeanAutoDao beanAuto = new BeanAutoDaoPlusImpl();
        if(beanAuto.createBean()){
            System.out.println("所有Bean实体类生成成功");
        }else{
            System.out.println("所有Bean实体类生成失败");
        }
        //生成repository
        RepositoryAutoDao repositoryAutoDao = new RepositoryAutoDaoImpl();
        if(repositoryAutoDao.createRepository()){
            System.out.println("所有repository实体类生成成功");
        }else{
            System.out.println("所有repository实体类生成失败");
        }
//生成service
        ServiceAutoDao serviceAutoDao = new ServiceAutoDaoImpl();
        if(serviceAutoDao.createService()){
            System.out.println("所有service实体类生成成功");
        }else{
            System.out.println("所有service实体类生成失败");
        }
//生成form
        FormAutoDao formAutoDao = new FormAutoDaoImpl();
        if(formAutoDao.createForm()){
            System.out.println("所有form实体类生成成功");
        }else{
            System.out.println("所有form实体类生成失败");
        }
//生成controller
        ControllerAutoDao controllerAutoDao = new ControllerAutoDaoImpl();
        if(controllerAutoDao.createController()){
            System.out.println("所有controller实体类生成成功");
        }else{
            System.out.println("所有controller实体类生成失败");
        }
//生成handler
        HandlerDao handlerDao = new HandlerDaoImpl();
        if(handlerDao.createHandler()){
            System.out.println("所有handler实体类生成成功");
        }else{
            System.out.println("所有handler实体类生成失败");
        }
//生成api
        ApiDocDaoImpl docDao = new ApiDocDaoImpl();
        if(docDao.createDoc()){
            System.out.println("所有api文档生成成功");
        }else{
            System.out.println("所有api文档生成失败");
        }




    }

    public static void main(String[] args) {
        new MainRunner().generateCode();

//        StringBuffer importCon=new StringBuffer();
//        importCon.append("insert into km_site(customerid,site_type,site_purpose,site_classification,site_address,site_phone,site_department,site_area,site_book,site_page,site_lat,site_lng,site_firstdate,site_status,site_photo_attachment,site_position_attachment,contract_number,contract_attachment,taxpayer_customerid) values ");
//        for(int i=0;i<1002000;i++){
//            if(i==1001999){
//                importCon.append(" ("+i+1+",1,1,1,'1"+i+"','1"+i+"',1,'123','123','123456789',12.1112525,22.1112525,'2018-08-14',1,1,1,'1"+i+"',"+i+","+i+");\n");
//            }else{
//                importCon.append(" ("+i+1+",1,1,1,'1"+i+"','1"+i+"',1,'123','123','123456789',12.1112525,22.1112525,'2018-08-14',1,1,1,'1"+i+"',"+i+","+i+"),\n");
//            }
//
//        }
//        FileUtil.createFileAtPath("D:/sql", "km_site.txt", importCon.toString());
//    }
    }
}
