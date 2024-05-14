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

/** This is an auto generated class representing the StudentAWS type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "StudentAWS", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class StudentAWS implements Model {
  public static final QueryField ID = field("StudentAWS", "id");
  public static final QueryField NAME = field("StudentAWS", "name");
  public static final QueryField STUDENT_ID = field("StudentAWS", "student_id");
  public static final QueryField PROFESSOR = field("StudentAWS", "professor");
  public static final QueryField EXAM = field("StudentAWS", "exam");
  public static final QueryField BARCODE = field("StudentAWS", "barcode");
  public static final QueryField GRADE = field("StudentAWS", "grade");
  public static final QueryField CLASSNAME = field("StudentAWS", "classname");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String", isRequired = true) String student_id;
  private final @ModelField(targetType="String", isRequired = true) String professor;
  private final @ModelField(targetType="String", isRequired = true) String exam;
  private final @ModelField(targetType="String", isRequired = true) String barcode;
  private final @ModelField(targetType="String", isRequired = true) String grade;
  private final @ModelField(targetType="String", isRequired = true) String classname;
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
  
  public String getName() {
      return name;
  }
  
  public String getStudentId() {
      return student_id;
  }
  
  public String getProfessor() {
      return professor;
  }
  
  public String getExam() {
      return exam;
  }
  
  public String getBarcode() {
      return barcode;
  }
  
  public String getGrade() {
      return grade;
  }
  
  public String getClassname() {
      return classname;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private StudentAWS(String id, String name, String student_id, String professor, String exam, String barcode, String grade, String classname) {
    this.id = id;
    this.name = name;
    this.student_id = student_id;
    this.professor = professor;
    this.exam = exam;
    this.barcode = barcode;
    this.grade = grade;
    this.classname = classname;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      StudentAWS studentAws = (StudentAWS) obj;
      return ObjectsCompat.equals(getId(), studentAws.getId()) &&
              ObjectsCompat.equals(getName(), studentAws.getName()) &&
              ObjectsCompat.equals(getStudentId(), studentAws.getStudentId()) &&
              ObjectsCompat.equals(getProfessor(), studentAws.getProfessor()) &&
              ObjectsCompat.equals(getExam(), studentAws.getExam()) &&
              ObjectsCompat.equals(getBarcode(), studentAws.getBarcode()) &&
              ObjectsCompat.equals(getGrade(), studentAws.getGrade()) &&
              ObjectsCompat.equals(getClassname(), studentAws.getClassname()) &&
              ObjectsCompat.equals(getCreatedAt(), studentAws.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), studentAws.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getStudentId())
      .append(getProfessor())
      .append(getExam())
      .append(getBarcode())
      .append(getGrade())
      .append(getClassname())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("StudentAWS {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("student_id=" + String.valueOf(getStudentId()) + ", ")
      .append("professor=" + String.valueOf(getProfessor()) + ", ")
      .append("exam=" + String.valueOf(getExam()) + ", ")
      .append("barcode=" + String.valueOf(getBarcode()) + ", ")
      .append("grade=" + String.valueOf(getGrade()) + ", ")
      .append("classname=" + String.valueOf(getClassname()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
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
  public static StudentAWS justId(String id) {
    return new StudentAWS(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      student_id,
      professor,
      exam,
      barcode,
      grade,
      classname);
  }
  public interface NameStep {
    StudentIdStep name(String name);
  }
  

  public interface StudentIdStep {
    ProfessorStep studentId(String studentId);
  }
  

  public interface ProfessorStep {
    ExamStep professor(String professor);
  }
  

  public interface ExamStep {
    BarcodeStep exam(String exam);
  }
  

  public interface BarcodeStep {
    GradeStep barcode(String barcode);
  }
  

  public interface GradeStep {
    ClassnameStep grade(String grade);
  }
  

  public interface ClassnameStep {
    BuildStep classname(String classname);
  }
  

  public interface BuildStep {
    StudentAWS build();
    BuildStep id(String id);
  }
  

  public static class Builder implements NameStep, StudentIdStep, ProfessorStep, ExamStep, BarcodeStep, GradeStep, ClassnameStep, BuildStep {
    private String id;
    private String name;
    private String student_id;
    private String professor;
    private String exam;
    private String barcode;
    private String grade;
    private String classname;
    public Builder() {
      
    }
    
    private Builder(String id, String name, String student_id, String professor, String exam, String barcode, String grade, String classname) {
      this.id = id;
      this.name = name;
      this.student_id = student_id;
      this.professor = professor;
      this.exam = exam;
      this.barcode = barcode;
      this.grade = grade;
      this.classname = classname;
    }
    
    @Override
     public StudentAWS build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new StudentAWS(
          id,
          name,
          student_id,
          professor,
          exam,
          barcode,
          grade,
          classname);
    }
    
    @Override
     public StudentIdStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public ProfessorStep studentId(String studentId) {
        Objects.requireNonNull(studentId);
        this.student_id = studentId;
        return this;
    }
    
    @Override
     public ExamStep professor(String professor) {
        Objects.requireNonNull(professor);
        this.professor = professor;
        return this;
    }
    
    @Override
     public BarcodeStep exam(String exam) {
        Objects.requireNonNull(exam);
        this.exam = exam;
        return this;
    }
    
    @Override
     public GradeStep barcode(String barcode) {
        Objects.requireNonNull(barcode);
        this.barcode = barcode;
        return this;
    }
    
    @Override
     public ClassnameStep grade(String grade) {
        Objects.requireNonNull(grade);
        this.grade = grade;
        return this;
    }
    
    @Override
     public BuildStep classname(String classname) {
        Objects.requireNonNull(classname);
        this.classname = classname;
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
    private CopyOfBuilder(String id, String name, String studentId, String professor, String exam, String barcode, String grade, String classname) {
      super(id, name, student_id, professor, exam, barcode, grade, classname);
      Objects.requireNonNull(name);
      Objects.requireNonNull(student_id);
      Objects.requireNonNull(professor);
      Objects.requireNonNull(exam);
      Objects.requireNonNull(barcode);
      Objects.requireNonNull(grade);
      Objects.requireNonNull(classname);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder studentId(String studentId) {
      return (CopyOfBuilder) super.studentId(studentId);
    }
    
    @Override
     public CopyOfBuilder professor(String professor) {
      return (CopyOfBuilder) super.professor(professor);
    }
    
    @Override
     public CopyOfBuilder exam(String exam) {
      return (CopyOfBuilder) super.exam(exam);
    }
    
    @Override
     public CopyOfBuilder barcode(String barcode) {
      return (CopyOfBuilder) super.barcode(barcode);
    }
    
    @Override
     public CopyOfBuilder grade(String grade) {
      return (CopyOfBuilder) super.grade(grade);
    }
    
    @Override
     public CopyOfBuilder classname(String classname) {
      return (CopyOfBuilder) super.classname(classname);
    }
  }
  

  public static class StudentAWSIdentifier extends ModelIdentifier<StudentAWS> {
    private static final long serialVersionUID = 1L;
    public StudentAWSIdentifier(String id) {
      super(id);
    }
  }
  
}
