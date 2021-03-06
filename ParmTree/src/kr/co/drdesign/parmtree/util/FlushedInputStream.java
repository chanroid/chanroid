package kr.co.drdesign.parmtree.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;


public class FlushedInputStream extends FilterInputStream {
	public FlushedInputStream(InputStream inputStream) {
		super(inputStream);
	}

	public long skip(long n) throws IOException {
		long totalBytesSkipped = 0L;
		while ( totalBytesSkipped < n ) {
			long bytesSkipped = in.skip(n - totalBytesSkipped);

			if(bytesSkipped == 0L) {
				int b = read();
				if( b < 0) {
					break;
				} else {
					bytesSkipped = 1;
				}
			}
			totalBytesSkipped += bytesSkipped;
		}
		return totalBytesSkipped;
	}
}