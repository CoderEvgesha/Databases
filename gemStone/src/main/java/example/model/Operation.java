package example.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;


@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Operation {
    private String name;
    private String initiator;
    private List<Reason> reasons;
}
