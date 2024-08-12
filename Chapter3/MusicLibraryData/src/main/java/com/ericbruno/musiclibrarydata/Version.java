package com.ericbruno.musiclibrarydata;

import java.util.StringJoiner;

@javax.annotation.processing.Generated(
    value = "com.kobylynskyi.graphql.codegen.GraphQLCodegen",
    date = "2024-06-09T15:40:28-0400"
)
public class Version implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String major;
    private String minor;

    public Version() {
    }

    public Version(String major, String minor) {
        this.major = major;
        this.minor = minor;
    }

    public String getMajor() {
        return major;
    }
    public void setMajor(String major) {
        this.major = major;
    }

    public String getMinor() {
        return minor;
    }
    public void setMinor(String minor) {
        this.minor = minor;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        if (major != null) {
            joiner.add("major: \"" + major + "\"");
        }
        if (minor != null) {
            joiner.add("minor: \"" + minor + "\"");
        }
        return joiner.toString();
    }

}
