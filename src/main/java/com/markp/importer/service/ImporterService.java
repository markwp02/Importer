package com.markp.importer.service;

import com.markp.importer.model.ProductMessage;

public interface ImporterService {

    public void processMessage(ProductMessage productMessage);
}
