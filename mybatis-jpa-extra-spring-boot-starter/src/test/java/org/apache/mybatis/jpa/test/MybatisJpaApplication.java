/*
 * Copyright [2021] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

package org.apache.mybatis.jpa.test;

import org.apache.mybatis.jpa.id.SerialGenerator;
import org.apache.mybatis.jpa.persistence.JpaPageResults;
import org.apache.mybatis.jpa.test.dao.service.StudentsService;
import org.apache.mybatis.jpa.test.entity.Students;
import org.apache.mybatis.jpa.util.WebContext;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = {"org.apache.mybatis.jpa"})
@MapperScan("org.apache.mybatis.jpa.test.dao.persistence")
public class MybatisJpaApplication implements ApplicationRunner{ 
    private static final Logger _logger = LoggerFactory.getLogger(MybatisJpaApplication.class);
    
    @Autowired
    StudentsService studentsService;
    
   // @Autowired
    SerialGenerator serialGenerator;
    @Autowired
    private ApplicationContext applicationContext;
    /**
     * @param args args
     */
    public static void main(String[] args) {
    	new SpringApplicationBuilder(MybatisJpaApplication.class) 
         .web(WebApplicationType.NONE) // .REACTIVE, .SERVLET 
         .bannerMode(Banner.Mode.OFF).run(args) ;
    	
    	//application.run(args);
    }
    
	@Override
	public void run(ApplicationArguments args) throws Exception {
		WebContext.applicationContext=applicationContext;
		 
		_logger.info("queryPageResults by mapperId...");
		 Students student=new Students();
		 student.setStdGender("M");
		 //student.setStdMajor(政治");
		 student.setPageSize(10);
		 student.setPageNumber(2);
		 JpaPageResults<Students>  jpaPageResults=studentsService.queryPageResults("queryPageResults1",student);
		 for (Students s : jpaPageResults.getRows()) {
			 _logger.info("Students "+s);
		 }
		
	}
	
}
