import com.google.common.collect.Maps;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FilterCyclic {
    public static void filterLinkLogicInsurance(Vector<Vector<Object>> linkToBV1) {

        //数据:[[5305,18652,5310,18678],[5310,18678,5304,18647],[5304,18647,5310,18678]]
        //取出所有产品
        Map<Long,Map<Long,Boolean>> allOptionsMap = new HashMap<Long, Map<Long,Boolean>>();
        Map<Long,Boolean> optionMap = Maps.newHashMap();
        optionMap.put(18652L,false);
        allOptionsMap.put(5305L,optionMap);
        optionMap.clear();
        optionMap.put(18678L,false);
        allOptionsMap.put(5310L,optionMap);


        //a解决中间可能出现的死循环
        ConcurrentLinkedQueue<ConcurrentLinkedQueue<Long>> concurrentLinkedQueues = new ConcurrentLinkedQueue<ConcurrentLinkedQueue<Long>>();

        //过滤
        Vector<Vector<Object>> linkToBV = new Vector<Vector<Object>>();
        for (int i = 0; i < linkToBV1.size(); i++) {
            Long linkInsuranceId = CommUtil.null2Long(linkToBV1.get(i).get(0));
            Long linkInsOptionId = CommUtil.null2Long(linkToBV1.get(i).get(1));
            Long linkedInsuranceId = CommUtil.null2Long(linkToBV1.get(i).get(2));
            Long linkedInsOptionId = CommUtil.null2Long(linkToBV1.get(i).get(3));

            linkToBV.add(linkToBV1.get(i));
            ConcurrentLinkedQueue<Long> concurrentLinkedQueue = new ConcurrentLinkedQueue<Long>();
            concurrentLinkedQueue.add(linkInsuranceId);
            concurrentLinkedQueue.add(linkInsOptionId);
            concurrentLinkedQueue.add(linkedInsuranceId);
            concurrentLinkedQueue.add(linkedInsOptionId);
            concurrentLinkedQueues.add(concurrentLinkedQueue);
        }

        //获取可能存在的循环,解决闭环
        Map<Long, Set<Long>> cicles = new HashMap<Long, Set<Long>>();
        getcicle(linkToBV, cicles);

        //判断是否有父节点,且父节点也要在cicles中,否则删除
        Map<Long, Set<Long>> cicleisnotcicles = new HashMap<Long, Set<Long>>();//记录不在cicle中的父节点,
        for (Map.Entry<Long, Set<Long>> cicle : cicles.entrySet()) {
            Long insId = cicle.getKey();
            Set<Long> optionIds = cicle.getValue();

            for (int i = 0; i < linkToBV.size(); i++) {
                Long linkInsuranceId = CommUtil.null2Long(linkToBV.get(i).get(0));
                Long linkInsOptionId = CommUtil.null2Long(linkToBV.get(i).get(1));
                Long linkedInsuranceId = CommUtil.null2Long(linkToBV.get(i).get(2));
                Long linkedInsOptionId = CommUtil.null2Long(linkToBV.get(i).get(3));
                if (linkedInsuranceId.equals(insId) && optionIds.contains(linkedInsOptionId)) {
                    if (cicles.get(linkInsuranceId) == null || (cicles.get(linkInsuranceId) != null && !cicles.get(linkInsuranceId).contains(linkInsOptionId))) {
                        //这才是正在的父节点
                        if (cicleisnotcicles.get(linkInsuranceId) != null) {
                            cicleisnotcicles.get(linkInsuranceId).add(linkInsOptionId);
                        } else {
                            Set<Long> set = new HashSet<Long>();
                            set.add(linkInsOptionId);
                            cicleisnotcicles.put(linkInsuranceId, set);
                        }
                    }
                }
            }
        }

        //b解决中间可能出现的死循环,先解决a->b->c->...->b
        Map<Long, Set<Long>> cicleMapForSingleCicle = new HashMap<Long, Set<Long>>();
        for (Map.Entry<Long, Set<Long>> cicleinsEntry : cicleisnotcicles.entrySet()) {
            Long insId = cicleinsEntry.getKey();
            for (Long optionId : cicleinsEntry.getValue()) {
                removeLinkfirst(insId, optionId, concurrentLinkedQueues, cicleMapForSingleCicle);
            }
        }

        linkToBV = new Vector<Vector<Object>>();
        Iterator<ConcurrentLinkedQueue<Long>> concurrentLinkedQueue = concurrentLinkedQueues.iterator();
        while (concurrentLinkedQueue.hasNext()) {
            ConcurrentLinkedQueue<Long> list = concurrentLinkedQueue.next();
            Iterator<Long> itea = list.iterator();
            Long linkInsId = -1L;
            Long linkInsOptionId = -1L;
            Long linkedInsId = -1L;
            Long linkedInsOptionId = -1L;
            int i = 0;
            while (itea.hasNext()) {
                Long next = itea.next();
                if (i == 0) {
                    linkInsId = next;
                } else if (i == 1) {
                    linkInsOptionId = next;
                } else if (i == 2) {
                    linkedInsId = next;
                } else if (i == 3) {
                    linkedInsOptionId = next;
                }
                i++;

            }
            Vector<Object> linkToV = new Vector<Object>();
            linkToV.add(linkInsId);
            linkToV.add(linkInsOptionId);
            linkToV.add(linkedInsId);
            linkToV.add(linkedInsOptionId);
            linkToBV.add(linkToV);
        }


        //获取 cicleisnotcicles 中已此开始的link 树 从cicles  中删除;因为这些不是循环树;
        for (Map.Entry<Long, Set<Long>> cicleinsEntry : cicleisnotcicles.entrySet()) {
            Long insId = cicleinsEntry.getKey();
            for (Long optionId : cicleinsEntry.getValue()) {
                removeCielcs(cicles, insId, optionId, linkToBV);
            }
        }


        //删除循环树中的一个节点;断开循环
        Set<Long> removeSet = new HashSet<Long>();
        Iterator<Long> iterator1 = cicles.keySet().iterator();
        while (iterator1.hasNext()) {
            Long key = iterator1.next();
            if (removeSet.contains(key)) {
                iterator1.remove();
                continue;
            }
            Set<Long> values = cicles.get(key);
            boolean flag = false;
            Iterator<Vector<Object>> linkToBVIterator = linkToBV.iterator();
            while (linkToBVIterator.hasNext()) {
                Vector<Object> linkToBVIds = linkToBVIterator.next();
                Long linkedInsuranceId1 = CommUtil.null2Long(linkToBVIds.get(2));
                Long linkedInsOptionId1 = CommUtil.null2Long(linkToBVIds.get(3));
                if (key.equals(linkedInsuranceId1) && values.contains(linkedInsOptionId1)) {
                    linkToBVIterator.remove();
                }
            }

            for (Long value : values) {
                List<LinkLogic> links = getLinks(linkToBV);
                Map<Long, Long> parent = new HashMap<Long, Long>();
                parent.put(key, value);
                LinkLogic linkLogic = new LinkLogic();
                linkLogic.setParentId(null);
                linkLogic.setId(parent);
                linkLogic.setChilds(buildLinks(links, parent));
                bulidRemove(linkLogic, removeSet);
            }


        }

        //封装
        List<LinkLogic> links = getLinks(linkToBV);

        Map<Long, Set<Long>> map = new HashMap<Long, Set<Long>>();

        //获取根节点
        getParentLists(linkToBV, map);
        //封装树结构
        List<LinkLogic> linkLogics = new ArrayList<LinkLogic>();
        for (Map.Entry<Long, Set<Long>> map_ : map.entrySet()) {
            Long insId = map_.getKey();
            for (Long optionId : map_.getValue()) {
                LinkLogic linkLogic = new LinkLogic();
                linkLogic.setParentId(null);
                Map<Long, Long> map2 = new HashMap();
                map2.put(insId, optionId);
                linkLogic.setId(map2);
                linkLogic.setChilds(buildLinks(links, map2));
                linkLogics.add(linkLogic);

            }
        }

        //设置子节点是否删除
        buildAllOptionsMap(allOptionsMap, linkLogics, false);
        //设置父节点是否删除
//        updateParentStatus(linkLogics);
        //根据节点的状态设置选项是否删除
        buildOptions(allOptionsMap, linkLogics);


    }


    /**
     * @param insId
     * @param optionId
     * @param concurrentLinkedQueues
     * @param cicleMapForSingleCicle
     */
    private static void removeLinkfirst(
            Long insId,
            Long optionId,
            ConcurrentLinkedQueue<ConcurrentLinkedQueue<Long>> concurrentLinkedQueues,
            Map<Long, Set<Long>> cicleMapForSingleCicle) {

        Iterator<ConcurrentLinkedQueue<Long>> iterator = concurrentLinkedQueues.iterator();
        while (iterator.hasNext()) {
            ConcurrentLinkedQueue<Long> list = iterator.next();
            Iterator<Long> itea = list.iterator();
            Long linkInsId = -1L;
            Long linkInsOptionId = -1L;
            Long linkedInsId = -1L;
            Long linkedInsOptionId = -1L;
            int i = 0;
            while (itea.hasNext()) {
                Long next = itea.next();
                if (i == 0) {
                    linkInsId = next;
                } else if (i == 1) {
                    linkInsOptionId = next;
                } else if (i == 2) {
                    linkedInsId = next;
                } else if (i == 3) {
                    linkedInsOptionId = next;
                }
                i++;

            }


            if (linkInsId.equals(insId) && optionId.equals(linkInsOptionId)) {
                if (cicleMapForSingleCicle.get(linkedInsId) != null && cicleMapForSingleCicle.get(linkedInsId).contains(linkedInsOptionId)) {
                    iterator.remove();
                    break;
                }
                if (cicleMapForSingleCicle.get(insId) != null) {
                    cicleMapForSingleCicle.get(insId).add(optionId);
                } else {
                    Set<Long> set = new HashSet<Long>();
                    set.add(optionId);
                    cicleMapForSingleCicle.put(insId, set);
                }
                removeLinkfirst(linkedInsId, linkedInsOptionId, concurrentLinkedQueues, cicleMapForSingleCicle);
            }

        }
    }


    /**
     * @param cicles
     * @param insId
     * @param optionId
     * @param linkToBV
     */
    private static void removeCielcs(Map<Long, Set<Long>> cicles, Long insId,
                                     Long optionId, Vector<Vector<Object>> linkToBV) {

        for (int i = 0; i < linkToBV.size(); i++) {
            Long linkInsuranceId = CommUtil.null2Long(linkToBV.get(i).get(0));
            Long linkInsOptionId = CommUtil.null2Long(linkToBV.get(i).get(1));
            Long linkedInsuranceId = CommUtil.null2Long(linkToBV.get(i).get(2));
            Long linkedInsOptionId = CommUtil.null2Long(linkToBV.get(i).get(3));

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
                removeCielcs(cicles, linkedInsuranceId, linkedInsOptionId, linkToBV);
            }
        }

    }


    /**
     * @param linkLogic
     * @param removeSet
     */
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


    /**
     * @param linkToBV
     * @param cicles
     */
    private static void getcicle(Vector<Vector<Object>> linkToBV,
                                 Map<Long, Set<Long>> cicles) {
        for (int i = 0; i < linkToBV.size(); i++) {
            Long linkInsuranceId = CommUtil.null2Long(linkToBV.get(i).get(0));
            Long linkInsOptionId = CommUtil.null2Long(linkToBV.get(i).get(1));
            boolean flag = false;
            for (int j = 0; j < linkToBV.size(); j++) {
                Long linkInsuranceId1 = CommUtil.null2Long(linkToBV.get(j).get(2));
                Long linkInsOptionId1 = CommUtil.null2Long(linkToBV.get(j).get(3));
                if (linkInsuranceId.equals(linkInsuranceId1) && linkInsOptionId.equals(linkInsOptionId1)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {

            } else {
                if (cicles.get(linkInsuranceId) != null) {
                    cicles.get(linkInsuranceId).add(linkInsOptionId);
                } else {
                    Set<Long> set = new HashSet<Long>();
                    set.add(linkInsOptionId);
                    cicles.put(linkInsuranceId, set);
                }
            }


        }
    }


    /**
     * @param linkLogics
     */
    private void updateParentStatus(List<LinkLogic> linkLogics) {
        for (LinkLogic linkLogic : linkLogics) {
            List<LinkLogic> childs = linkLogic.getChilds();
            boolean flag = false;
            for (LinkLogic child : childs) {
                if (!child.isDel()) {
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                linkLogic.setDel(true);
            }
        }
    }


    /**
     * @param allOptionsMap
     * @param linkLogics
     */

    private static void buildOptions(Map<Long, Map<Long, Boolean>> allOptionsMap,
                                     List<LinkLogic> linkLogics) {
        for (LinkLogic linkLogic : linkLogics) {
            Map<Long, Long> linkIds = linkLogic.getId();
            Map<Long, Long> linkParentIds = linkLogic.getParentId();
            List<LinkLogic> childs = linkLogic.getChilds();
            Long insId = -1L;
            Long optionId = -1L;
            for (Map.Entry<Long, Long> linkId : linkIds.entrySet()) {
                insId = linkId.getKey();
                optionId = linkId.getValue();
            }

            if (linkLogic.isDel()) {
                if (allOptionsMap.get(insId) != null && allOptionsMap.get(insId).keySet().contains(optionId)) {
                    allOptionsMap.get(insId).put(optionId, true);
                    buildOptions(allOptionsMap, childs);

                } else {
                    buildOptions(allOptionsMap, childs);
                }
            } else {
                buildOptions(allOptionsMap, childs);
            }

        }

    }


    /**
     * @param allOptionsMap
     * @param linkLogics
     */
    private static void buildAllOptionsMap(
            Map<Long, Map<Long, Boolean>> allOptionsMap,
            List<LinkLogic> linkLogics, boolean parentStatus) {
        for (LinkLogic linkLogic : linkLogics) {
            Map<Long, Long> linkIds = linkLogic.getId();
            Map<Long, Long> linkParentIds = linkLogic.getParentId();
            List<LinkLogic> childs = linkLogic.getChilds();
            Long insId = -1L;
            Long optionId = -1L;
            for (Map.Entry<Long, Long> linkId : linkIds.entrySet()) {
                insId = linkId.getKey();
                optionId = linkId.getValue();
            }

            if (allOptionsMap.get(insId) == null || !allOptionsMap.get(insId).keySet().contains(optionId) || parentStatus) {
                linkLogic.setDel(true);
                buildAllOptionsMap(allOptionsMap, childs, linkLogic.isDel());
            } else {
                buildAllOptionsMap(allOptionsMap, childs, linkLogic.isDel());

                boolean flag = false;
                for (LinkLogic child : childs) {
                    if (!child.isDel()) {
                        flag = true;
                        break;
                    }
                }

                if (childs.size() > 0 && !flag) {
                    linkLogic.setDel(true);
                }
            }

        }
    }


    /**
     * @param links
     * @param parnetId
     * @return
     */
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

    /**
     * @param linkToBV
     * @param map
     */
    private static void getParentLists(Vector<Vector<Object>> linkToBV,
                                       Map<Long, Set<Long>> map) {
        Map<Map<Long, Long>, Integer> maps = new HashMap<Map<Long, Long>, Integer>();
        for (int i = 0; i < linkToBV.size(); i++) {
            Long linkInsuranceId = CommUtil.null2Long(linkToBV.get(i).get(0));
            Long linkInsOptionId = CommUtil.null2Long(linkToBV.get(i).get(1));
            boolean flag = false;
            for (int j = 0; j < linkToBV.size(); j++) {
                Long linkedInsuranceId1 = CommUtil.null2Long(linkToBV.get(j).get(2));
                Long linkInsOptionId1 = CommUtil.null2Long(linkToBV.get(j).get(3));
                if (linkInsuranceId.equals(linkedInsuranceId1) && linkInsOptionId.equals(linkInsOptionId1)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                if (map.get(linkInsuranceId) != null) {
                    map.get(linkInsuranceId).add(linkInsOptionId);
                } else {
                    Set<Long> set = new HashSet<Long>();
                    set.add(linkInsOptionId);
                    map.put(linkInsuranceId, set);
                }
            }

        }
    }


    /**
     * @param map1
     * @param map2
     * @return
     */
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


    private static List<LinkLogic> getLinks(Vector<Vector<Object>> linkToBV) {
        List<LinkLogic> links = new ArrayList<LinkLogic>();
        for (int i = 0; i < linkToBV.size(); i++) {
            LinkLogic linkLogic = new LinkLogic();
            Long linkInsuranceId = CommUtil.null2Long(linkToBV.get(i).get(0));
            Long linkInsOptionId = CommUtil.null2Long(linkToBV.get(i).get(1));
            Long linkedInsuranceId = CommUtil.null2Long(linkToBV.get(i).get(2));
            Long linkedInsOptionId = CommUtil.null2Long(linkToBV.get(i).get(3));
            Map parent = new HashMap<Long, Long>();
            parent.put(linkInsuranceId, linkInsOptionId);
            Map children = new HashMap<Long, Long>();
            children.put(linkedInsuranceId, linkedInsOptionId);
            linkLogic.setId(children);
            linkLogic.setParentId(parent);
            links.add(linkLogic);

        }

        return links;
    }
}
