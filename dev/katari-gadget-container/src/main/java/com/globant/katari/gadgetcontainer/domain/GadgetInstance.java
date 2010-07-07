package com.globant.katari.gadgetcontainer.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.lang.Validate;

/**
 * Represents an instance of a {@link Gadget}.
 * 
 * This one contains the information that the xml-to-html shinding
 * implementations needs to perform the rendering, also gives the rpc support.
 * 
 * @author waabox(emiliano[dot]arango[at]globant[dot]com)
 */
@Entity
public class GadgetInstance {
  
  /**
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  
  /** @link String} that identifies the url of the gadget xml spec.
   */
  @Column(nullable = false)
  private String url;
  
  /** {@link String} that identifies the owner of this gadget.
   */
  @Column(nullable = false)
  private String user;
  
  /** {@link String} that identifies the position on the UI. eg: 1#1
   */
  @Column(nullable = true)
  private String gadgetPosition;
  
  /** {@link String} that identifies the user and the application when the
   * gadget accesses the open social container.
   * 
   * This token is passed to the GadgetInstance whenever the user requests a
   * new page generation. It is never null.
   * 
   * Note: This is an implementation artifact to easy the generation of the
   * json/xml/whatever output.
   */
  @Transient
  private String securityToken;
  
  /** {@link String} with the user who is accessing to the application.
   * 
   * See the note on securityToken. This is never null.
   */
  @Transient
  private String viewer;
  
  /**
   * Hibernate constructor.
   */
  GadgetInstance() {
    
  }

  /** Creates a new Gadget instance for the given user.
   * 
   * @param canvasUser {@link String} user for this gadget instance. 
   * It cannot be null.
   * @param gadgetUrl {@link String} with the url of the gadget xml. 
   * Cannot be empty.
   * @param position {@link String} representation of the gadget position
   * in the page. The format is defined by the client side implementation (for
   * example, "3#2" for a column based layout. It cannot be empty.
   */
  public GadgetInstance(final String canvasUser, final String gadgetUrl,
      final String position) {
    Validate.notEmpty(canvasUser, "user can not be null");
    Validate.notEmpty(gadgetUrl, "gadget url can not be empty");
    Validate.notEmpty(position, "position can not be null");
    url = gadgetUrl;
    user = canvasUser;
    gadgetPosition = position;
  }

  /** Sets the security token for the current user and application, also 
   * sets the person who is requesting access to the application.
   * 
   * This operation is intended to be called only when the user requests the
   * page.
   * 
   * @param theSecurityToken {@link String} the securityToken to set.
   * Cannot be empty.
   * @param gadgetViewer {@link String} the viewer to set. Can not be empty.
   */
  public void associateToViewer(final String theSecurityToken,
      final String theViewer) {
    Validate.notEmpty(theSecurityToken, "securityToken can not be null");
    Validate.notEmpty(theViewer, "viewer can not be null");
    viewer = theViewer;
    securityToken = theSecurityToken;    
  }
  
  /** @return long the id of the gadget instance.
   */
  public long getId() {
    return id;
  }
  
  /** @return {@link String} location of the gadget xml spec.
   */
  public String getUrl() {
    return url;
  }
  
  /** @return @link{User} the user (owner) of this gadget.
   */
  public String getUser() {
    return user;
  }
  
  /** @return @link{String} the securityToken
   */
  public String getSecurityToken() {
    return securityToken;
  }
  
  /** @return @link{String} the current gadget viewer. 
   */
  public String getViewer() {
    return viewer;
  }

  /** @return @link{String} the position inside the page of this gadget.
   */
  public String getGadgetPosition() {
    return gadgetPosition;
  }
  
  /** Change the actual position of the gadget.
   * @param newPosition {@link String} the new position. Can not be empty.
   */
  public void move(final String newPosition) {
    Validate.notEmpty(newPosition, "the gadget new position can not be empty");
    gadgetPosition = newPosition;
  }
}