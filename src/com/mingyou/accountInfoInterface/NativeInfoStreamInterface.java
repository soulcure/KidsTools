/**
 * 
 */
package com.mingyou.accountInfoInterface;

import com.mykj.comm.io.TDataInputStream;
import com.mykj.comm.io.TDataOutputStream;

/**
 * @author Jason
 * 
 */
public interface NativeInfoStreamInterface {
	void writeToStream(TDataOutputStream dos);

	void readFromStream(TDataInputStream dis);
}
