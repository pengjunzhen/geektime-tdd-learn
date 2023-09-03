package org.example.args;

class StringOptionParser extends IntOptionParser {

    private StringOptionParser() {
        super(String::valueOf);
    }

}
