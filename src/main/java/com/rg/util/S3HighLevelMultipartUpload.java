package com.rg.util;

/*
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.UUID;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
//import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.AmazonS3Encryption;
import com.amazonaws.services.s3.AmazonS3EncryptionClientBuilder;
import com.amazonaws.services.s3.internal.SSEResultBase;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.CryptoConfiguration;
import com.amazonaws.services.s3.model.CryptoMode;
import com.amazonaws.services.s3.model.EncryptionMaterials;
import com.amazonaws.services.s3.model.GetObjectRequest;
//import com.amazonaws.services.s3.model.AccessControlList;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.Grant;
//import com.amazonaws.services.s3.model.GroupGrantee;
//import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.StaticEncryptionMaterialsProvider;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
//import com.amazonaws.services.s3.model.CanonicalGrantee;
*/

public class S3HighLevelMultipartUpload {

	/*
	public static void s3Upload1(String filePath) throws IOException {
        String clientRegion = "ap-northeast-2";
        String bucketName = "jisblee.me";
        String stringObjKeyName = "*** String object key name ***";
        String fileObjKeyName = "*** File object key name ***";
        String fileName = "*** Path to file to upload ***";

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .withCredentials(new ProfileCredentialsProvider())
                    .build();
        
            // Upload a text string as a new object.
            s3Client.putObject(bucketName, stringObjKeyName, "Uploaded String Object");
            
            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("plain/text");
            metadata.addUserMetadata("x-amz-meta-title", "someTitle");
            request.setMetadata(metadata);
            s3Client.putObject(request);
        }
        catch(AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
        }
        catch(SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }
    
    
    public static void s3Upload(String filePath, String serverFileName) throws Exception {
    //public static void main(String[] args) throws Exception {
        //String clientRegion = "AP_NORTHEAST_2";
        String clientRegion = "ap-northeast-2";
        String bucketName = "jisblee.me";
        String keyName = URLEncoder.encode("123", "UTF-8") + "/" + serverFileName;
        
		//String encodeResult = URLEncoder.encode("12345", "UTF-8") + "/";
        // String filePath = "*** Path for file to upload ***";
        
        //System.setProperty("com.amazonaws.sdk.disableCertChecking", "true");
        
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
                    //.withCredentials(new AWSStaticCredentialsProvider(credentials))
                    //.withCannedAcl(CannedAccessControlList.PublicRead)
                    .build();
            
            //ObjectListing listing = s3Client.listObjects("someBucket", "foo/");
            
            
            //ArrayList<Grant> grantCollection = new ArrayList<Grant>();
            //Grant grant1 = new Grant(new CanonicalGrantee(s3Client.getS3AccountOwner().getId()), Permission.Read);
            //grantCollection.add(grant1);
            //AccessControlList bucketAcl = new AccessControlList();
            //bucketAcl.grantAllPermissions(grantCollection.toArray(new Grant[0]));
            ////s3Client.setBucketAcl(bucketName, bucketAcl);
            //s3Client.setObjectAcl(bucketName, keyName, bucketAcl);
            
            
            
            TransferManager tm = TransferManagerBuilder.standard()
                    .withS3Client(s3Client)
                    .build();
            
            // TransferManager processes all transfers asynchronously,
            // so this call returns immediately.
            Upload upload = tm.upload(bucketName, keyName, new File(filePath));
            System.out.println("Object upload started");
    
            // Optionally, wait for the upload to finish before continuing.
            upload.waitForCompletion();
            System.out.println("Object upload complete");
            

            
            // Get the existing object ACL that we want to modify.
            //AccessControlList acl = s3Client.getObjectAcl(bucketName, keyName);
            
            // Clear the existing list of grants.
            //acl.getGrantsAsList().clear();
            
            // Grant a sample set of permissions, using the existing ACL owner for Full Control permissions.
            //acl.grantPermission(new CanonicalGrantee(acl.getOwner().getId()), Permission.FullControl);
            //acl.grantPermission(new EmailAddressGrantee(emailGrantee), Permission.WriteAcp);
    
            // Save the modified ACL back to the object.
            //s3Client.setObjectAcl(bucketName, keyName, acl);
            
            
        }
        catch(AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
        }
        catch(SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }

    public static void s3UploadEncryption1(String filePath, String serverFileName) throws Exception {
        Regions clientRegion = Regions.AP_NORTHEAST_2;
        String bucketName = "jisblee.me";
        String objectKeyName = "test2/123.txt";
        String masterKeyDir = System.getProperty("java.io.tmpdir");
        String masterKeyName = "secret.key";

        // Generate a symmetric 256-bit AES key.
        KeyGenerator symKeyGenerator = KeyGenerator.getInstance("AES");
        symKeyGenerator.init(256);
        SecretKey symKey = symKeyGenerator.generateKey();

        // To see how it works, save and load the key to and from the file system.
        saveSymmetricKey(masterKeyDir, masterKeyName, symKey);
        symKey = loadSymmetricAESKey(masterKeyDir, masterKeyName, "AES");

        //System.setProperty("com.amazonaws.sdk.disableCertChecking", "true");
        
        try {
            // Create the Amazon S3 encryption client.
            EncryptionMaterials encryptionMaterials = new EncryptionMaterials(symKey);
            AmazonS3 s3EncryptionClient = AmazonS3EncryptionClientBuilder.standard()
                    .withCredentials(new ProfileCredentialsProvider())
                    .withEncryptionMaterials(new StaticEncryptionMaterialsProvider(encryptionMaterials))
                    .withRegion(clientRegion)
                    .build();

            // Upload a new object. The encryption client automatically encrypts it.
            //byte[] plaintext = "S3 Object Encrypted Using Client-Side Symmetric Master Key.".getBytes();
            //s3EncryptionClient.putObject(new PutObjectRequest(bucketName,
                    //objectKeyName,
                    //new ByteArrayInputStream(plaintext),
                    //new ObjectMetadata()));
            
            s3EncryptionClient.putObject(new PutObjectRequest(bucketName,
                    objectKeyName,
                    new File("/home/ubuntu/download/123.txt")
                    ));
            
            
            //PutObjectRequest putRequest = new PutObjectRequest(bucketName,
            		   //keyName, file).withSSEAwsKeyManagementParams(new SSEAwsKeyManagementParams());
            
            
            // Download and decrypt the object.
            //S3Object downloadedObject = s3EncryptionClient.getObject(bucketName, objectKeyName);
            //byte[] decrypted = com.amazonaws.util.IOUtils.toByteArray(downloadedObject.getObjectContent());

            // Verify that the data that you downloaded is the same as the original data.
            //System.out.println("Plaintext: " + new String(plaintext));
            //System.out.println("Decrypted text: " + new String(decrypted));
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }

    private static void saveSymmetricKey(String masterKeyDir, String masterKeyName, SecretKey secretKey) throws IOException {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(secretKey.getEncoded());
        FileOutputStream keyOutputStream = new FileOutputStream(masterKeyDir + File.separator + masterKeyName);
        keyOutputStream.write(x509EncodedKeySpec.getEncoded());
        keyOutputStream.close();
    }

    private static SecretKey loadSymmetricAESKey(String masterKeyDir, String masterKeyName, String algorithm)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        // Read the key from the specified file.
        File keyFile = new File(masterKeyDir + File.separator + masterKeyName);
        FileInputStream keyInputStream = new FileInputStream(keyFile);
        byte[] encodedPrivateKey = new byte[(int) keyFile.length()];
        keyInputStream.read(encodedPrivateKey);
        keyInputStream.close();

        // Reconstruct and return the master key.
        return new SecretKeySpec(encodedPrivateKey, "AES");
    }
    
    
    
    
    
    

    public static void s3UploadEncryption2(String filePath, String serverFileName) {
        Regions clientRegion = Regions.AP_NORTHEAST_2;
        String bucketName = "jisblee.me";
        String keyNameToEncrypt = "test2/abc.txt";
        String keyNameToCopyAndEncrypt = "test2/abc_1.txt";
        String copiedObjectKeyName = "test2/abc_2.txt";

        //System.setProperty("com.amazonaws.sdk.disableCertChecking", "true");
        
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .withCredentials(new ProfileCredentialsProvider())
                    .build();

            // Upload an object and encrypt it with SSE.
            uploadObjectWithSSEEncryption(s3Client, bucketName, keyNameToEncrypt);

            // Upload a new unencrypted object, then change its encryption state
            // to encrypted by making a copy.
            changeSSEEncryptionStatusByCopying(s3Client,
                    bucketName,
                    keyNameToCopyAndEncrypt,
                    copiedObjectKeyName, "");
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }

    private static void uploadObjectWithSSEEncryption(AmazonS3 s3Client, String bucketName, String keyName) {
        String objectContent = "Test object encrypted with SSE";
        byte[] objectBytes = objectContent.getBytes();

        // Specify server-side encryption.
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(objectBytes.length);
        objectMetadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
        PutObjectRequest putRequest = new PutObjectRequest(bucketName,
                keyName,
                new ByteArrayInputStream(objectBytes),
                objectMetadata);

        // Upload the object and check its encryption status.
        PutObjectResult putResult = s3Client.putObject(putRequest);
        System.out.println("Object \"" + keyName + "\" uploaded with SSE.");
        printEncryptionStatus(putResult);
    }

    private static void changeSSEEncryptionStatusByCopying(AmazonS3 s3Client,
                                                           String bucketName,
                                                           String sourceKey,
                                                           String destKey,
                                                           String file) {
        // Upload a new, unencrypted object.
        PutObjectResult putResult = s3Client.putObject(bucketName, sourceKey, new File(file));
        System.out.println("Unencrypted object \"" + sourceKey + "\" uploaded.");
        printEncryptionStatus(putResult);

        // Make a copy of the object and use server-side encryption when storing the copy.
        CopyObjectRequest request = new CopyObjectRequest(bucketName,
                sourceKey,
                bucketName,
                destKey);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
        request.setNewObjectMetadata(objectMetadata);

        // Perform the copy operation and display the copy's encryption status.
        CopyObjectResult response = s3Client.copyObject(request);
        System.out.println("Object \"" + destKey + "\" uploaded with SSE.");
        printEncryptionStatus(response);

        // Delete the original, unencrypted object, leaving only the encrypted copy in Amazon S3.
        s3Client.deleteObject(bucketName, sourceKey);
        System.out.println("Unencrypted object \"" + sourceKey + "\" deleted.");
    }

    private static void printEncryptionStatus(SSEResultBase response) {
        String encryptionStatus = response.getSSEAlgorithm();
        if (encryptionStatus == null) {
            encryptionStatus = "Not encrypted with SSE";
        }
        System.out.println("Object encryption status is: " + encryptionStatus);
    }
    

    

    /*
     * Strict authenticated encryption mode does not support ranged GETs. This is because we must use AES/CTR for ranged
     * GETs which is not an authenticated encryption algorithm. To do a partial get using authenticated encryption you have to
     * get the whole object and filter to the data you want.
     /
    public static void s3UploadEncryption(String filePath, String serverFileName) throws NoSuchAlgorithmException {

        //System.setProperty("com.amazonaws.sdk.disableCertChecking", "true");
        
        
    	//SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
        //AmazonS3Encryption s3Encryption = AmazonS3EncryptionClientBuilder
                //.standard()
                //.withRegion(Regions.AP_NORTHEAST_2)
                //.withCryptoConfiguration(new CryptoConfiguration(CryptoMode.StrictAuthenticatedEncryption))
                //.withEncryptionMaterials(new StaticEncryptionMaterialsProvider(new EncryptionMaterials(secretKey)))
                //.build();

        //s3Encryption.putObject("jisblee.me", "test2/aNewTwo.txt", new File("/home/ubuntu/download/abcd_2.txt"));
        

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_2)
                .withCredentials(new ProfileCredentialsProvider())
                .build();

        changeSSEEncryptionStatusByCopying(s3Client,
        		"jisblee.me",
        		"test2/aNewFour.txt",
        		"test2/aNewFour_1.txt",
        		"/home/ubuntu/download/Create_Table_1.txt");
        
        //try {
            //s3Encryption.getObject(new GetObjectRequest("jisblee.me", "test2/aNewOne.txt").withRange(0, 2));
        //} catch (SecurityException e) {
            //System.err.println("Range GET is not supported with authenticated encryption");
        //}
        
    }
    */
}
