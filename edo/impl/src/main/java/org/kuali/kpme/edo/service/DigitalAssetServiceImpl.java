package org.kuali.kpme.edo.service;

import org.kuali.rice.core.api.config.property.ConfigContext;

import java.io.*;
import java.util.UUID;

public class DigitalAssetServiceImpl implements DigitalAssetService {

    @Override
    public InputStream getAsset(UUID uid,String prefix) throws FileNotFoundException {
        return new FileInputStream(new File(""));
    }

    @Override
    public UUID storeAsset(InputStream is, String prefix) throws IOException {
        UUID uuid = UUID.randomUUID();

        String uploadPath = makePath(prefix);
        File destinationDirectory = new File(uploadPath);
        destinationDirectory.mkdirs();

        FileOutputStream fos = new FileOutputStream(new File(uploadPath,uuid.toString()));
        fos.write(is.read());
        fos.flush();
        fos.close();
        is.close();

        return uuid;
    }

    private String makePath(String prefix) {
        String path=ConfigContext.getCurrentContextConfig().getProperty("edo.upload.path");
        assert(prefix.length()>2);
        path+=File.separator+ prefix.substring(0,2)+ prefix +File.separator;
        return path;
    }



}
