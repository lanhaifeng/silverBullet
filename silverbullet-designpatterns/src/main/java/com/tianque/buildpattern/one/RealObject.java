package com.tianque.buildpattern.one;

import java.util.List;

/**
 * 《建造者模式》
 * 优点：易于解耦
 将产品本身与产品创建过程进行解耦，可以使用相同的创建过程来得到不同的产品。也就说细节依赖抽象。
 易于精确控制对象的创建
 将复杂产品的创建步骤分解在不同的方法中，使得创建过程更加清晰
 易于拓展
 增加新的具体建造者无需修改原有类库的代码，易于拓展，符合“开闭原则“。
 * 缺点：建造者模式所创建的产品一般具有较多的共同点，其组成部分相似；如果产品之间的差异性很大，则不适合使用建造者模式，因此其使用范围受到一定的限制。
 如果产品的内部变化复杂，可能会导致需要定义很多具体建造者类来实现这种变化，导致系统变得很庞大。
 * 应用场景：
 需要生成的产品对象有复杂的内部结构，这些产品对象具备共性；
 隔离复杂对象的创建和使用，并使得相同的创建过程可以创建不同的产品。
 */
public class RealObject {
    private List<String> parts;

    public List<String> getParts() {
        return parts;
    }

    public void setParts(List<String> parts) {
        this.parts = parts;
    }

    public void addPart(String part) {
        parts.add(part);
    }
}
