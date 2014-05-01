/**
 * Copyright 2004-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.khr.hub.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Helper {

	
	public static void switchFrame(String framename, WebDriver driver)
	{
		WebElement frame = driver.findElement(By.xpath("//*[contains(@name, '" + framename + "')]"));
		String frameID = frame.getAttribute("name");
        driver.switchTo().frame(frameID);
	}
	
	public static void switchToDefaultFrame(WebDriver driver)
	{
		driver.switchTo().defaultContent();
	}

    public static void waitHere()
    {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
