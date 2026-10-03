package com.bank.ccr.model;
import jakarta.persistence.*;
@Entity
public class Counterparty {
  @Id private String id;
  private String legalName;
  private String lei;
  private String parentGroup;
  private String country;
  protected Counterparty() {}
  public Counterparty(String id, String legalName, String lei, String parentGroup, String country) {this.id=id;this.legalName=legalName;this.lei=lei;this.parentGroup=parentGroup;this.country=country;}
  public String getId(){return id;} public String getLegalName(){return legalName;} public String getLei(){return lei;} public String getParentGroup(){return parentGroup;} public String getCountry(){return country;}
}
