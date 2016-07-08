package uk.co.codera.test.io;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;

class MetadataXmlFilter implements IOFileFilter {

    private static final String FILENAME_PREFIX = "METADATA";
    private static final String FILENAME_SUFFIX = ".xml";

    @Override
    public boolean accept(File dir, String name) {
        return accept(name);
    }

    @Override
    public boolean accept(File file) {
        return accept(file.getName());
    }

    private boolean accept(String name) {
        return name.startsWith(FILENAME_PREFIX) && name.endsWith(FILENAME_SUFFIX);
    }
}