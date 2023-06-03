package com.fatec.starvingless.dto;

import com.fatec.starvingless.entities.Comment;
import com.fatec.starvingless.entities.Post;
import com.fatec.starvingless.entities.User;
import com.fatec.starvingless.services.exceptions.Exception;
import com.fatec.starvingless.services.exceptions.InvalidDateException;
import com.fatec.starvingless.services.exceptions.InvalidNumberOfCommentsException;
import com.fatec.starvingless.services.exceptions.InvalidPhoneException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotBlank(message = "Required field")
    @javax.validation.constraints.Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s\\-!]*$",
            message = "O título não pode conter caracteres especiais")
    @Size(min = 10, max = 50, message = "O título deve ter entre 10 e 50 caracteres")
    private String title;
    @javax.validation.constraints.Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s\\-!,?]*$",
            message = "A descrição não pode conter caracteres especiais")
    @Size(max = 250, message = "A descrição deve ter entre 10 e 250 caracteres")
    private String description;

    private URL imageUrl;
    @NotBlank(message = "Required field")
    private String createDate;
    private boolean threadOpen;
//    private Integer numberOfComments;

    private Long userId;

    private String firstName;

//    private List<CommentDTO> comments;


    public PostDTO(Post post, String firstName) {
        id = post.getId();
        title = post.getTitle();
        description = post.getDescription();
        imageUrl = post.getImage();
        createDate = post.getCreateDate();
        threadOpen = post.isThreadOpen();
        this.userId = post.getId();
        this.firstName = firstName;
//
    }

//    public void setNumberOfComments(Integer numberOfComments) {
//        if (numberOfComments != null && numberOfComments < 0) {
//            throw new InvalidNumberOfCommentsException("The number of comments must be a positive integer.");
//        }
//        this.numberOfComments = numberOfComments;
//    }


    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public void setCreateDate(String createDate) {
        if (createDateIsValid(createDate)) {
            try {
                Date date = DATE_FORMAT.parse(createDate);
                Date currentDate = new Date();
                if (date.after(currentDate)) {
                    throw new InvalidDateException("SignUpDate cannot be greater than the current date");
                }
                this.createDate = createDate;
            } catch (ParseException e) {
                throw new InvalidDateException("Ex: dd/MM/yyyy");
            }
        } else {
            throw new InvalidDateException("Ex: dd/MM/yyyy");
        }
    }

    private boolean createDateIsValid(String createDate) {
        try {
            Date date = DATE_FORMAT.parse(createDate);
            return createDate.equals(DATE_FORMAT.format(date));
        } catch (ParseException e) {
            return false;
        }
    }



}
