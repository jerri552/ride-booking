package com.rst.Models;

import com.rst.Enums.DocumentStatus;

public class DriverDocument {

    private DocumentStatus status;

    public DriverDocument() {
        this.status = DocumentStatus.PENDING;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }
}