/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package net.nuttle.avro.bo.account;  
@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class AvroAccountResponse extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"AvroAccountResponse\",\"namespace\":\"net.nuttle.avro.bo.account\",\"fields\":[{\"name\":\"id\",\"type\":\"int\"},{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"balance\",\"type\":\"float\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public int id;
  @Deprecated public java.lang.CharSequence name;
  @Deprecated public float balance;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use {@link \#newBuilder()}. 
   */
  public AvroAccountResponse() {}

  /**
   * All-args constructor.
   */
  public AvroAccountResponse(java.lang.Integer id, java.lang.CharSequence name, java.lang.Float balance) {
    this.id = id;
    this.name = name;
    this.balance = balance;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return name;
    case 2: return balance;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: id = (java.lang.Integer)value$; break;
    case 1: name = (java.lang.CharSequence)value$; break;
    case 2: balance = (java.lang.Float)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'id' field.
   */
  public java.lang.Integer getId() {
    return id;
  }

  /**
   * Sets the value of the 'id' field.
   * @param value the value to set.
   */
  public void setId(java.lang.Integer value) {
    this.id = value;
  }

  /**
   * Gets the value of the 'name' field.
   */
  public java.lang.CharSequence getName() {
    return name;
  }

  /**
   * Sets the value of the 'name' field.
   * @param value the value to set.
   */
  public void setName(java.lang.CharSequence value) {
    this.name = value;
  }

  /**
   * Gets the value of the 'balance' field.
   */
  public java.lang.Float getBalance() {
    return balance;
  }

  /**
   * Sets the value of the 'balance' field.
   * @param value the value to set.
   */
  public void setBalance(java.lang.Float value) {
    this.balance = value;
  }

  /** Creates a new AvroAccountResponse RecordBuilder */
  public static net.nuttle.avro.bo.account.AvroAccountResponse.Builder newBuilder() {
    return new net.nuttle.avro.bo.account.AvroAccountResponse.Builder();
  }
  
  /** Creates a new AvroAccountResponse RecordBuilder by copying an existing Builder */
  public static net.nuttle.avro.bo.account.AvroAccountResponse.Builder newBuilder(net.nuttle.avro.bo.account.AvroAccountResponse.Builder other) {
    return new net.nuttle.avro.bo.account.AvroAccountResponse.Builder(other);
  }
  
  /** Creates a new AvroAccountResponse RecordBuilder by copying an existing AvroAccountResponse instance */
  public static net.nuttle.avro.bo.account.AvroAccountResponse.Builder newBuilder(net.nuttle.avro.bo.account.AvroAccountResponse other) {
    return new net.nuttle.avro.bo.account.AvroAccountResponse.Builder(other);
  }
  
  /**
   * RecordBuilder for AvroAccountResponse instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<AvroAccountResponse>
    implements org.apache.avro.data.RecordBuilder<AvroAccountResponse> {

    private int id;
    private java.lang.CharSequence name;
    private float balance;

    /** Creates a new Builder */
    private Builder() {
      super(net.nuttle.avro.bo.account.AvroAccountResponse.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(net.nuttle.avro.bo.account.AvroAccountResponse.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.name)) {
        this.name = data().deepCopy(fields()[1].schema(), other.name);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.balance)) {
        this.balance = data().deepCopy(fields()[2].schema(), other.balance);
        fieldSetFlags()[2] = true;
      }
    }
    
    /** Creates a Builder by copying an existing AvroAccountResponse instance */
    private Builder(net.nuttle.avro.bo.account.AvroAccountResponse other) {
            super(net.nuttle.avro.bo.account.AvroAccountResponse.SCHEMA$);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.name)) {
        this.name = data().deepCopy(fields()[1].schema(), other.name);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.balance)) {
        this.balance = data().deepCopy(fields()[2].schema(), other.balance);
        fieldSetFlags()[2] = true;
      }
    }

    /** Gets the value of the 'id' field */
    public java.lang.Integer getId() {
      return id;
    }
    
    /** Sets the value of the 'id' field */
    public net.nuttle.avro.bo.account.AvroAccountResponse.Builder setId(int value) {
      validate(fields()[0], value);
      this.id = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'id' field has been set */
    public boolean hasId() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'id' field */
    public net.nuttle.avro.bo.account.AvroAccountResponse.Builder clearId() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'name' field */
    public java.lang.CharSequence getName() {
      return name;
    }
    
    /** Sets the value of the 'name' field */
    public net.nuttle.avro.bo.account.AvroAccountResponse.Builder setName(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.name = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'name' field has been set */
    public boolean hasName() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'name' field */
    public net.nuttle.avro.bo.account.AvroAccountResponse.Builder clearName() {
      name = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'balance' field */
    public java.lang.Float getBalance() {
      return balance;
    }
    
    /** Sets the value of the 'balance' field */
    public net.nuttle.avro.bo.account.AvroAccountResponse.Builder setBalance(float value) {
      validate(fields()[2], value);
      this.balance = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'balance' field has been set */
    public boolean hasBalance() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'balance' field */
    public net.nuttle.avro.bo.account.AvroAccountResponse.Builder clearBalance() {
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    public AvroAccountResponse build() {
      try {
        AvroAccountResponse record = new AvroAccountResponse();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.Integer) defaultValue(fields()[0]);
        record.name = fieldSetFlags()[1] ? this.name : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.balance = fieldSetFlags()[2] ? this.balance : (java.lang.Float) defaultValue(fields()[2]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}