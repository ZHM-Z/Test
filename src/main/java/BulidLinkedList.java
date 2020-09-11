/*
import com.google.common.collect.Sets;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

public class BulidLinkedList {


    public static void main(String[] args) {
        ConcurrentSkipListSet<ConcurrentSkipListSet<Long>> lists = new ConcurrentSkipListSet<ConcurrentSkipListSet<Long>>();

        ConcurrentSkipListSet<Long> list = new ConcurrentSkipListSet<Long>();
        ConcurrentSkipListSet<Long> list1 = new ConcurrentSkipListSet<Long>();
        ConcurrentSkipListSet<Long> list2 = new ConcurrentSkipListSet<Long>();

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

        Map<Long, Set<Long>> cicles = new HashMap<Long, Set<Long>>();
        getcicle(lists, cicles);
        System.out.println(cicles);

        Map<Long, Set<Long>> cicleisnotcicles = new HashMap<Long, Set<Long>>();//记录不在cicle中的父节点,
        Set<Long> setmap = Sets.newHashSet();
        setmap.add(18651L);
        setmap.add(18652L);


        cicleisnotcicles.put(5305L, setmap);


        //判断是否还有父节点,且父节点也要在cicle中,否则删除

        //获取 cicleisnotcicles 中已此开始的link 树 从cicles  中删除;因为这些不是循环树;
        for (Map.Entry<Long, Set<Long>> cicleinsEntry : cicleisnotcicles.entrySet()) {
            Long insId = cicleinsEntry.getKey();
            for (Long optionId : cicleinsEntry.getValue()) {
                removeCielcs(cicles, insId, optionId, lists);
            }
        }


        System.out.println(cicles);

//        Map<Long, Set<Long>> map = new HashMap<Long, Set<Long>>();
//
//        getParentLists(lists, map);
//        System.out.println(map);
//
//        System.out.println(list.lists);
//
//        List<LinkLogic> links = getLinks(lists);
//
//
//        List<LinkLogic> linkLogics = new ArrayList<LinkLogic>();
//        for(Map.Entry<Long,Set<Long>> map_:map.entrySet()){
//            Long insId = map_.getKey();
//            for(Long optionId : map_.getValue()){
//                LinkLogic linkLogic = new LinkLogic();
//                linkLogic.setParentId(null);
//                Map<Long,Long> map2 = new HashMap();
//                map2.put(insId,optionId);
//                linkLogic.setId(map2);
//                linkLogic.setChilds(buildLinks(links, map2));
//                linkLogics.add(linkLogic);
//            }
//        }
//
//        //TODO:解决闭环，取其一
//        System.out.println(linkLogics);
//        buildLinks(links);

    }

    private static void removeCielcs(Map<Long, Set<Long>> cicles, Long insId, Long optionId, ConcurrentSkipListSet<ConcurrentSkipListSet<Long>> lists) {
        for (int i = 0; i < lists.size(); i++) {
            Long linkInsuranceId = lists.get(i).get(0);
            Long linkInsOptionId = lists.get(i).get(1);
            Long linkedInsuranceId = lists.get(i).get(2);
            Long linkedInsOptionId = lists.get(i).get(3);

            if (insId.equals(linkInsuranceId) && optionId.equals(linkInsOptionId)) {
                Iterator<Long> iterator = cicles.keySet().iterator();
                while (iterator.hasNext()) {
                    Long key = iterator.next();
                    if (key.equals(linkedInsuranceId)) {
                        if (cicles.get(key).contains(linkedInsOptionId)) {
                            cicles.get(key).remove(linkedInsOptionId);
                        }
                        if (cicles.get(key).size() == 0) {
                            iterator.remove();
                        }
                    }

                }
                removeCielcs(cicles, linkedInsuranceId, linkedInsOptionId, lists);
            }
        }
    }

    private static void bulidRemove(LinkLogic linkLogic, Set<Long> removeSet) {
        Map<Long, Long> insAndOptionIdMap = linkLogic.getId();
        for (Map.Entry<Long, Long> insAndOptionId : insAndOptionIdMap.entrySet()) {
            removeSet.add(insAndOptionId.getKey());
        }

        List<LinkLogic> childs = linkLogic.getChilds();

        for (LinkLogic child : childs) {
            bulidRemove(child, removeSet);
        }

    }

    private static void getcicle(ConcurrentSkipListSet<ConcurrentSkipListSet<Long>> lists, Map<Long, Set<Long>> cicle) {
        {
            Map<Map<Long, Long>, Integer> maps = new HashMap<Map<Long, Long>, Integer>();
            for (int i = 0; i < lists.size(); i++) {
                Long linkInsId = lists.get(i).get(0);
                Long linkInsOptionId = lists.get(i).get(1);
                boolean flag = false;
                for (int j = 0; j < lists.size(); j++) {
                    Long linkedInsId1 = lists.get(j).get(2);
                    Long linkedInsOptionId1 = lists.get(j).get(3);

                    if (linkInsId.equals(linkedInsId1) && linkInsOptionId.equals(linkedInsOptionId1)) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {

                } else {
                    if (cicle.get(linkInsId) != null) {
                        cicle.get(linkInsId).add(linkInsOptionId);
                    } else {
                        Set<Long> set = new HashSet<Long>();
                        set.add(linkInsOptionId);
                        cicle.put(linkInsId, set);
                    }
                }


            }
        }

    }

    private static void getParentLists(List<List<Long>> lists, Map<Long, Set<Long>> map) {
        Map<Map<Long, Long>, Integer> maps = new HashMap<Map<Long, Long>, Integer>();
        for (int i = 0; i < lists.size(); i++) {
            Long linkInsId = lists.get(i).get(0);
            Long linkInsOptionId = lists.get(i).get(1);
            boolean flag = false;
            for (int j = 0; j < lists.size(); j++) {
                Long linkedInsId1 = lists.get(j).get(2);

                if (linkInsId.equals(linkedInsId1)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                if (map.get(linkInsId) != null) {
                    map.get(linkInsId).add(linkInsOptionId);
                } else {
                    Set<Long> set = new HashSet<Long>();
                    set.add(linkInsOptionId);
                    map.put(linkInsId, set);
                }
            }


        }
    }

    private static void buildParentTree(List<LinkLogic> linkLogics, Map<Long, Set<Long>> map, Map<Long, Long> id, Map<Long, Long> parentId) {
        for (int i = 0; i < linkLogics.size(); i++) {
            LinkLogic linkLogic = linkLogics.get(i);
            Map<Long, Long> parentId_ = linkLogic.getParentId();
            Map<Long, Long> id_ = linkLogic.getId();
            if (isSameMap(id, parentId_)) {
                buildParentTree(linkLogics, map, id_, parentId_);
            } else {
                buildParentTree(linkLogics, map, id_, parentId_);
            }

        }
    }

    private static List<LinkLogic> buildLinks(List<LinkLogic> links, Map<Long, Long> parnetId) {

        List<LinkLogic> menus = new ArrayList<LinkLogic>();
        for (LinkLogic link : links) {
            Map<Long, Long> id = link.getId();
            Map<Long, Long> pid = link.getParentId();

            if (isSameMap(parnetId, pid)) {
                List<LinkLogic> childs = buildLinks(links, id);
                link.setChilds(childs);
                menus.add(link);
            }
        }
        return menus;
    }

    public static boolean isSameMap(Map<Long, Long> map1, Map<Long, Long> map2) {
        boolean isSame = false;
        for (Map.Entry<Long, Long> entry1 : map1.entrySet()) {
            Long m1value = entry1.getValue();
            Long m2value = map2.get(entry1.getKey());
            if (m1value.equals(m2value)) {
                isSame = true;
            }
        }
        return isSame;
    }


    private static List<LinkLogic> getLinks(List<List<Long>> lists) {
        List<LinkLogic> links = new ArrayList<LinkLogic>();
        for (int i = 0; i < lists.size(); i++) {
            LinkLogic linkLogic = new LinkLogic();
            Long linkInsId = lists.get(i).get(0);
            Long linkInsOptionId = lists.get(i).get(1);
            Long linkedInsId = lists.get(i).get(2);
            Long linkedInsOptionId = lists.get(i).get(3);
            Map parent = new HashMap<Long, Long>();
            parent.put(linkInsId, linkInsOptionId);
            Map children = new HashMap<Long, Long>();
            children.put(linkedInsId, linkedInsOptionId);
            linkLogic.setId(children);
            linkLogic.setParentId(parent);
            links.add(linkLogic);

        }

        return links;
    }


    public static void buildTree(List<List<Long>> lists, List<LinkedList<Long>> result) {
        LinkedList<Long> linkedList = new LinkedList<Long>();
        for (int i = 0; i < lists.size(); i++) {
            Long linkInsId = lists.get(i).get(0);
            Long linkInsOptionId = lists.get(i).get(1);
            Long linkedInsId = lists.get(i).get(2);
            Long linkedInsOptionId = lists.get(i).get(3);
            linkedList.addAll(lists.get(i));
            for (int j = 0; j < lists.size(); j++) {
                List<Long> longList = lists.get(j);
                if (longList.contains(linkInsId) && longList.contains(linkInsOptionId)) ;

            }


        }


    }


    private static void buildTree1(List<List<Long>> lists, List<LinkLogic> linkLogics) {
        for (int i = 0; i < lists.size(); i++) {
            Long linkInsId = lists.get(i).get(0);
            Long linkInsOptionId = lists.get(i).get(1);
            Long linkedInsId = lists.get(i).get(2);
            Long linkedInsOptionId = lists.get(i).get(3);

        }


    }


}
*/
