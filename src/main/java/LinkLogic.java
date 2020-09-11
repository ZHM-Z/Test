import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class LinkLogic {
    private Map<Long, Long> id;

    private Map<Long,Long> parentId;

   private LinkLogic parent;

   private List<LinkLogic> childs;

}
