package com.rg.util;

/*
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.MultipleFileDownload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

import java.io.File;
import java.net.URLEncoder;
*/

public class S3Download {
	
	/*
	public static void downloadFile(String bucket_name, String key_name, String file_path) {
		
		//String bucket_name = "";
		//String key_name = "";
		//String file_path = "";
		
		//System.setProperty("com.amazonaws.sdk.disableCertChecking", "true");
		
		//System.setProperty("javax.net.ssl.trustStore", "/home/ubuntu/certs_aws/jssecacerts");
		//System.setProperty("javax.net.ssl.trustStorePassword", "changeit"); 
		
		
		//File f = new File(file_path);
		//TransferManager xfer_mgr = TransferManagerBuilder.standard().build();
		//try {
		    //Download xfer = xfer_mgr.download(bucket_name, key_name, f);
		    // loop with Transfer.isDone()
		    //XferMgrProgress.showTransferProgress(xfer);
		    // or block with Transfer.waitForCompletion()
		    //XferMgrProgress.waitForCompletion(xfer);
		//} catch (AmazonServiceException e) {
		    //System.err.println(e.getErrorMessage());
		    //System.exit(1);
		//}
		//xfer_mgr.shutdownNow();
		
		
        String clientRegion = "ap-northeast-2";
        String bucketName = "jisblee.me";
        //String keyName = URLEncoder.encode("123", "UTF-8") + "/" + serverFileName;
        //String keyName = "20190910/20190905.zip";
        String keyName = "test2/Create Table.txt";
        
        try {
        	
            //AWSCredentials creds = new BasicAWSCredentials(accessKey, secretKey);
            //String accessKey = "AKIAYPDQ6PIDX27M7ZVO";
            //String secretKey = "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";
            //BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
            //AWSCredentials credentials = new ProfileCredentialsProvider().getCredentials();

            //Grant grant2 = new Grant(GroupGrantee.LogDelivery, Permission.Read);
            //grantCollection.add(grant2);
            
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .withCredentials(new ProfileCredentialsProvider())
                    .build();
            
            TransferManager tm = TransferManagerBuilder.standard()
                    .withS3Client(s3Client)
                    .build();
            
            // TransferManager processes all transfers asynchronously,
            // so this call returns immediately.
            Download download = tm.download(bucketName, keyName, new File("/home/ubuntu/download/Create_Table.txt"));
            System.out.println("Object upload started");
    
            // Optionally, wait for the upload to finish before continuing.
            
			download.waitForCompletion();
            System.out.println("Object upload complete");
            

		} catch (AmazonClientException | InterruptedException e) {
			e.printStackTrace();
		}

	}
	*/
}
