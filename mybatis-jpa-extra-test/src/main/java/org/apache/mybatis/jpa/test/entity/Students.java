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
 

package org.apache.mybatis.jpa.test.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.mybatis.jpa.persistence.JpaBaseEntity;



/*
   ID                   varchar(40)                    not null,
   NAME                 varchar(60)                    not null,
   STATUS               char(1)                        null,
   CREATEBY             varchar(40)                    null,
   CREATEDATE           date                           null,
   UPDATEBY             varchar(40)                    null,
   UPDATEDATE           date                           null,
   constraint PK_ROLES primary key clustered (ID)
 */
/**
 * @author Crystal.Sea
 *
 */
@Entity
@Table(name = "STUDENTS")  
public class Students extends JpaBaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6928570405840778151L;
	
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO,generator="snowflakeid")
	//@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_MYBATIS_STUD")
	//@GeneratedValue(strategy=GenerationType.IDENTITY,generator="SEQ_MYBATIS_STUD")
	private String id;
	@Column
	private String stdNo;
	@Column
	private String stdName;
	@Column
	private String stdGender;
	@Column
	private int stdAge;
	@Column
	private String stdMajor;
	@Column
	private String stdClass;
	
	
	public Students() {
		super();
	}


	public String getStdNo() {
		return stdNo;
	}


	public void setStdNo(String stdNo) {
		this.stdNo = stdNo;
	}


	public String getStdName() {
		return stdName;
	}


	public void setStdName(String stdName) {
		this.stdName = stdName;
	}





	public String getStdGender() {
		return stdGender;
	}


	public void setStdGender(String stdGender) {
		this.stdGender = stdGender;
	}


	public int getStdAge() {
		return stdAge;
	}


	public void setStdAge(int stdAge) {
		this.stdAge = stdAge;
	}


	public String getStdMajor() {
		return stdMajor;
	}


	public void setStdMajor(String stdMajor) {
		this.stdMajor = stdMajor;
	}


	public String getStdClass() {
		return stdClass;
	}


	public void setStdClass(String stdClass) {
		this.stdClass = stdClass;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "Students [stdNo=" + stdNo + ", stdName=" + stdName + ", stdgender=" + stdGender + ", stdAge=" + stdAge
				+ ", stdMajor=" + stdMajor + ", stdClass=" + stdClass + "]";
	}


	
	

}
