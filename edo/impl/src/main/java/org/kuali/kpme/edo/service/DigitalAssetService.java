package org.kuali.kpme.edo.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public interface DigitalAssetService {
    InputStream getAsset(UUID uid,String prefix) throws FileNotFoundException;

    UUID storeAsset(InputStream is, String decorator) throws IOException;
}
