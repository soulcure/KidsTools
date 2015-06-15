/**
 * 
 */
package com.mingyou.accountInfoInterface;

import java.util.ArrayList;

/**
 * @author Jason
 * 
 */
public interface LoginInfoManagerInterface {

	void loadNativeInfo();

	void saveNativeInfo();

	boolean hasNativeInfo();
	
	void deleteCurAccountInfo();
	 
	void deleteAccountInfo(AccountInfoInterface acc);
	
	void updateAccountInfo(AccountInfoInterface acc);
	
	ArrayList<AccountInfoInterface> getAllAccountInfo();
	
}
