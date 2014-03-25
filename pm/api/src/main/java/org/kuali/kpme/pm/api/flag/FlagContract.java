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
package org.kuali.kpme.pm.api.flag;


import java.util.List;

/**
 * <p>FlagContract interface</p>
 *
 */
public interface FlagContract {
	
	/**
	 * The ID for the Flag object which Classification Flag and pstnFlag extend
	 * 
	 * <p>
	 * pmFlagId of a Flag.
	 * <p>
	 * 
	 * @return pmFlagId for Flag
	 */
	public String getPmFlagId();

	/**
	 * A grouping of flags
	 * 
	 * <p>
	 * category of a Flag
	 * <p>
	 * 
	 * @return category for Flag
	 */
	public String getCategory();

	/**
	 * The name of the Flag
	 * 
	 * <p>
	 * A descriptive name for the flag.
	 * <p>
	 * 
	 * @return names for Flag
	 */
	//public String getNames(); // KPME-2360/2958
	public List<String> getNames();

}
