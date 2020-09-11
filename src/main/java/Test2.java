import com.google.common.collect.Sets;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

public class Test2 {

    public static void main(String[] args) {
        ConcurrentLinkedQueue<ConcurrentLinkedQueue<Long>> lists = new ConcurrentLinkedQueue<ConcurrentLinkedQueue<Long>>();

        ConcurrentLinkedQueue<Long> list = new ConcurrentLinkedQueue<Long>();
        ConcurrentLinkedQueue<Long> list1 = new ConcurrentLinkedQueue<Long>();
        ConcurrentLinkedQueue<Long> list2 = new ConcurrentLinkedQueue<Long>();

        list.add(5305L);
        list.add(18652L);
        list.add(5310L);
        list.add(18678L);

        list1.add(5310L);
        list1.add(18678L);
        list1.add(5304L);
        list1.add(18647L);

        list2.add(5304L);
        list2.add(18647L);
        list2.add(5310L);
        list2.add(18678L);
        lists.add(list);
        lists.add(list1);
        lists.add(list2);
        Map<Long,Set<Long>> cicleisnotcicles = new HashMap<Long, Set<Long>>();
        Set<Long> sets=Sets.newHashSet();
        sets.add(18652L);
        cicleisnotcicles.put(5305L, sets);
        Map<Long, Set<Long>> cicleMapForSingleCicle = new HashMap<Long, Set<Long>>();
        //TODO:循环找出死循环
        //获取 cicleisnotcicles 中已此开始的link 树 从cicles  中删除;因为这些不是循环树;
        for(Map.Entry<Long, Set<Long>> cicleinsEntry:cicleisnotcicles.entrySet()){
            Long insId = cicleinsEntry.getKey();
            for(Long optionId:cicleinsEntry.getValue()){
                removeLinkfirst(insId,optionId,lists,cicleMapForSingleCicle);
            }
        }

        System.out.println(lists);

    }

    private static void removeLinkfirst(Long insId, Long optionId, ConcurrentLinkedQueue<ConcurrentLinkedQueue<Long>> lists, Map<Long, Set<Long>> cicleMapForSingleCicle) {

        Iterator<ConcurrentLinkedQueue<Long>> iterator = lists.iterator();
        while (iterator.hasNext()){
            ConcurrentLinkedQueue<Long> list = iterator.next();
            Iterator<Long> itea = list.iterator();
            Long linkInsId = -1L;
            Long linkInsOptionId = -1L;
            Long linkedInsId = -1L;
            Long linkedInsOptionId = -1L;
            int i=0;
            while (itea.hasNext()){
                Long next = itea.next();
                if(i==0){
                    linkInsId=next;
                }else if(i==1){
                    linkInsOptionId = next;
                } else if(i==2){
                    linkedInsId=next;
                }else if(i==3){
                    linkedInsOptionId=next;
                }
                i++;

            }


            if(linkInsId.equals(insId)&&optionId.equals(linkInsOptionId)){
                if(cicleMapForSingleCicle.get(linkedInsId)!=null&&cicleMapForSingleCicle.get(linkedInsId).contains(linkedInsOptionId)){
                    iterator.remove();
                    break;
                }
                if (cicleMapForSingleCicle.get(insId) != null) {
                    cicleMapForSingleCicle.get(insId).add(optionId);
                }else{
                    Set set = new HashSet();
                    set.add(optionId);
                    cicleMapForSingleCicle.put(insId,set);
                }
                removeLinkfirst(linkedInsId,linkedInsOptionId,lists,cicleMapForSingleCicle);
            }

        }
    }

}
