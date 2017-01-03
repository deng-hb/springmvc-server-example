package com.denghb.server.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.denghb.server.domain.JsonResult;
import com.denghb.server.utils.FileType;
import com.denghb.server.utils.FileTypeJudge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class IndexController {

	private Logger log = LoggerFactory.getLogger(IndexController.class);


	@RequestMapping(value = "/")
	@ResponseBody
	public JsonResult index() {
		return new JsonResult("index");
	}

	@RequestMapping(value = "/post", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String post(HttpServletRequest request) {

		String amount = request.getParameter("amount");
		log.debug("amount:" + amount);
		String name = request.getParameter("name");
		log.debug("name:" + name);
		return "success";
	}

	@RequestMapping("/upload")
	@ResponseBody
	public String upload(@RequestParam("images") CommonsMultipartFile[] images, HttpServletRequest request) {

		String amount = request.getParameter("amount");
		log.debug("amount:" + amount);
		
		for (int i = 0; i < images.length; i++) {
			System.out.println("fileName---------->" + images[i].getOriginalFilename());

			if (!images[i].isEmpty()) {
				int pre = (int) System.currentTimeMillis();
				try {
					// 拿到上传文件的输入流
					InputStream in = images[i].getInputStream();

					FileType ft = FileTypeJudge.getType(images[i].getInputStream());
					String filename = images[i].getOriginalFilename();
					if(null != ft){
						filename += "."+ft;
					}
					// 拿到输出流，同时重命名上传的文件
					FileOutputStream os = new FileOutputStream(
							"target/" + new Date().getTime() + filename);
					
					// 以写字节的方式写文件
					int b = 0;
					while ((b = in.read()) != -1) {
						os.write(b);
					}
					os.flush();
					os.close();
					in.close();
					int finaltime = (int) System.currentTimeMillis();
					System.out.println(finaltime - pre);

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("上传出错");
				}
			}
		}
		return "success";
	}

	@RequestMapping("/download")
	public void download(HttpServletRequest request,HttpServletResponse response) throws IOException{

		System.out.println("download start");
		System.out.println(request.getSession().getServletContext().getRealPath("/"));
//      String fileName = request.getSession().getServletContext().getRealPath("/") + "assets/c.dmg";
      String fileName = request.getSession().getServletContext().getRealPath("/") + "assets/alipay.png";
//        String fileName = request.getSession().getServletContext().getRealPath("/") + "../../../../../../Downloads/alipay.png";//QQ_V5.0.1.dmg


        File file = new File(fileName);

        System.out.println(file.getName());
        FileInputStream inputStream = new FileInputStream(file);
        byte[] data = new byte[(int)file.length()];
        inputStream.close();

//        response.setContentLength((int)file.length());

        OutputStream stream = response.getOutputStream();
        stream.write(data);
        stream.flush();
        stream.close();
		System.out.println("download end");
	}
}
