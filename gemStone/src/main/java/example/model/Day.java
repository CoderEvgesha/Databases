package example.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Day {
    private Integer status;

    public void close() {
        this.status = 2;
    }

    public void open() {
        this.status = 1;
    }
}
