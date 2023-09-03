package org.example.args;

class StringOptionParser extends IntOptionParser {

    @Override
    protected Object parseValue(String value) {
        return String.valueOf(value);
    }
}
