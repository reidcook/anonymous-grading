package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.ModelIdentifier;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the CoursesAWS type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "CoursesAWS", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class CoursesAWS implements Model {
  public static final QueryField ID = field("CoursesAWS", "id");
  public static final QueryField COURSENAME = field("CoursesAWS", "coursename");
  public static final QueryField PROFESSOR = field("CoursesAWS", "professor");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String coursename;
  private final @ModelField(targetType="String", isRequired = true) String professor;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  /** @deprecated This API is internal to Amplify and should not be used. */
  @Deprecated
   public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public String getCoursename() {
      return coursename;
  }
  
  public String getProfessor() {
      return professor;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private CoursesAWS(String id, String coursename, String professor) {
    this.id = id;
    this.coursename = coursename;
    this.professor = professor;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      CoursesAWS coursesAws = (CoursesAWS) obj;
      return ObjectsCompat.equals(getId(), coursesAws.getId()) &&
              ObjectsCompat.equals(getCoursename(), coursesAws.getCoursename()) &&
              ObjectsCompat.equals(getProfessor(), coursesAws.getProfessor()) &&
              ObjectsCompat.equals(getCreatedAt(), coursesAws.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), coursesAws.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getCoursename())
      .append(getProfessor())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("CoursesAWS {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("coursename=" + String.valueOf(getCoursename()) + ", ")
      .append("professor=" + String.valueOf(getProfessor()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static CoursenameStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static CoursesAWS justId(String id) {
    return new CoursesAWS(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      coursename,
      professor);
  }
  public interface CoursenameStep {
    ProfessorStep coursename(String coursename);
  }
  

  public interface ProfessorStep {
    BuildStep professor(String professor);
  }
  

  public interface BuildStep {
    CoursesAWS build();
    BuildStep id(String id);
  }
  

  public static class Builder implements CoursenameStep, ProfessorStep, BuildStep {
    private String id;
    private String coursename;
    private String professor;
    public Builder() {
      
    }
    
    private Builder(String id, String coursename, String professor) {
      this.id = id;
      this.coursename = coursename;
      this.professor = professor;
    }
    
    @Override
     public CoursesAWS build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new CoursesAWS(
          id,
          coursename,
          professor);
    }
    
    @Override
     public ProfessorStep coursename(String coursename) {
        Objects.requireNonNull(coursename);
        this.coursename = coursename;
        return this;
    }
    
    @Override
     public BuildStep professor(String professor) {
        Objects.requireNonNull(professor);
        this.professor = professor;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String coursename, String professor) {
      super(id, coursename, professor);
      Objects.requireNonNull(coursename);
      Objects.requireNonNull(professor);
    }
    
    @Override
     public CopyOfBuilder coursename(String coursename) {
      return (CopyOfBuilder) super.coursename(coursename);
    }
    
    @Override
     public CopyOfBuilder professor(String professor) {
      return (CopyOfBuilder) super.professor(professor);
    }
  }
  

  public static class CoursesAWSIdentifier extends ModelIdentifier<CoursesAWS> {
    private static final long serialVersionUID = 1L;
    public CoursesAWSIdentifier(String id) {
      super(id);
    }
  }
  
}
