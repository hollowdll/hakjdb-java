package com.github.hakjdb.hakjdbjava.params;

public class ChangeDatabaseOptions {
  private final String newName;
  private final String newDescription;

  private ChangeDatabaseOptions(String newName, String newDescription) {
    this.newName = newName;
    this.newDescription = newDescription;
  }

  public String getNewName() {
    return newName;
  }

  public String getNewDescription() {
    return newDescription;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private String newName = null;
    private String newDescription = null;

    public ChangeDatabaseOptions build() {
      return new ChangeDatabaseOptions(newName, newDescription);
    }

    /**
     * Sets the new database name.
     * @param newName New name of the database. Null does not change the name.
     * @return Builder
     */
    public Builder newName(String newName) {
      this.newName = newName;
      return this;
    }

    /**
     * Sets the new database description.
     * @param newDescription New description of the database. Null does not change the description.
     * @return Builder
     */
    public Builder newDescription(String newDescription) {
      this.newDescription = newDescription;
      return this;
    }

    @Override
    public String toString() {
      return "Builder{"
          + "newName='"
          + newName
          + '\''
          + ", newDescription='"
          + newDescription
          + '\''
          + '}';
    }
  }
}
