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

import org.apache.mybatis.jpa.id.UUIDHexGenerator;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UUIDHexGeneratorTest {
	private static final Logger _logger = LoggerFactory.getLogger(UUIDHexGeneratorTest.class);
	@Test
	public  void generator() {
		// TODO Auto-generated method stub
		UUIDHexGenerator uhg=new UUIDHexGenerator();
		_logger.info(uhg.generate(""));
		_logger.info(uhg.generate(""));
	}

}
