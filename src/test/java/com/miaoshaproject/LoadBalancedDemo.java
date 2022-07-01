package com.miaoshaproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author wangshuo
 * @Date 2022/6/16, 20:07
 * node参数：
 * weight: 配置文件中指定的该后端的权重，这个值是固定不变的。
 * effective_weight: 后端的有效权重，初始值为weight，程序报错后减一。
 * current_weight: 后端目前的权重，一开始为0，之后会动态调整
 */
public class LoadBalancedDemo {

    //调用接口
    public interface Invoker {
        Boolean isAvailable();
        String id();
    }
    //约定的invoker和权重的键值对
    final private List<Node> nodes;
    private static class Node implements Comparable<Node> {
        final Invoker invoker;
        final Integer weight;
        Integer effectiveWeight;
        Integer currentWeight;

        Node(Invoker invoker, Integer weight) {
            this.invoker = invoker;
            this.weight = weight;
            this.effectiveWeight = weight;
            this.currentWeight = 0;
        }

        @Override
        public int compareTo(Node o) {
            return currentWeight > o.currentWeight ? 1 : (currentWeight.equals(o.currentWeight) ? 0 : -1);
        }

        public void onInvokeSuccess() {
            if (effectiveWeight < this.weight)
                effectiveWeight++;
        }

        public void onInvokeFail() {
            effectiveWeight--;
        }
    }

    //构造
    public LoadBalancedDemo(Map<Invoker, Integer> invokersWeight) {
        if (invokersWeight != null && !invokersWeight.isEmpty()) {
            nodes = new ArrayList<>(invokersWeight.size());
            invokersWeight.forEach((invoker, weight) -> nodes.add(new Node(invoker, weight)));
        } else
            nodes = null;
    }

    /**
     * 算法逻辑：
     * 1. 对于每个请求，遍历集群中的所有可用后端，对于每个后端peer执行：
     *      peer->current_weight += peer->effective_weight。
     *      同时累加所有peer的effective_weight，保存为total。
     * 2. 从集群中选出current_weight最大的peer，作为本次选定的后端。
     * 3. 对于本次选定的后端，执行：peer->current_weight -= total。
     *
     */
    public Invoker select() {
        if (!checkNodes())
            return null;
        else if (nodes.size() == 1) {
            if (nodes.get(0).invoker.isAvailable())
                return nodes.get(0).invoker;
            else
                return null;
        }
        Integer total = 0;
        Node nodeOfMaxWeight = null;
        for (Node node : nodes) {
            total += node.effectiveWeight;
            node.currentWeight += node.effectiveWeight;

            if (nodeOfMaxWeight == null) {
                nodeOfMaxWeight = node;
            } else {
                nodeOfMaxWeight = nodeOfMaxWeight.compareTo(node) > 0 ? nodeOfMaxWeight : node;
            }
        }

        nodeOfMaxWeight.currentWeight -= total;
        return nodeOfMaxWeight.invoker;
    }

    public void onInvokeSuccess(Invoker invoker) {
        if (checkNodes()) {
            nodes.stream()
                    .filter((Node node) -> invoker.id().equals(node.invoker.id()))
                    .findFirst()
                    .get()
                    .onInvokeSuccess();
        }
    }

    public void onInvokeFail(Invoker invoker) {
        if (checkNodes()) {
            nodes.stream()
                    .filter((Node node) -> invoker.id().equals(node.invoker.id()))
                    .findFirst()
                    .get()
                    .onInvokeFail();
        }
    }

    private boolean checkNodes() {
        return (nodes != null && nodes.size() > 0);
    }

    public void printCurrentWeightBeforeSelect() {
        if (checkNodes()) {
            final StringBuffer out = new StringBuffer("{");
            nodes.forEach(node -> out.append(node.invoker.id())
                    .append("=")
                    .append(node.currentWeight + node.effectiveWeight)
                    .append(","));
            out.append("}");
            System.out.print(out);
        }
    }

    public void printCurrentWeight() {
        if (checkNodes()) {
            final StringBuffer out = new StringBuffer("{");
            nodes.forEach(node -> out.append(node.invoker.id())
                    .append("=")
                    .append(node.currentWeight)
                    .append(","));
            out.append("}");
            System.out.print(out);
        }
    }

    static Invoker initialize(String str){
        return new Invoker() {
            @Override
            public Boolean isAvailable() {
                return true;
            }
            @Override
            public String id() {
                return str;
            }
        };
    }

    public static void main(String[] args) {
        //new实验数据
        Map<Invoker, Integer> invokersWeight = new HashMap<>(3);
        invokersWeight.put(initialize("a"), 4);
        invokersWeight.put(initialize("b"), 2);
        invokersWeight.put(initialize("c"), 1);
        int times = 7;
        //test start
        LoadBalancedDemo roundRobin = new LoadBalancedDemo(invokersWeight);
        for (int i = 1; i <= times; i++) {
            System.out.print("第" + i + "次请求    选中前数据");
            roundRobin.printCurrentWeightBeforeSelect();
            Invoker invoker = roundRobin.select();
            System.out.print("    本次选中" + invoker.id() + "    选中后数据");
            roundRobin.printCurrentWeight();
            System.out.println();
        }
    }
}
