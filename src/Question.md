
- implements Serializable：序列化
  - 一个对象需要通过Object流来读写，对象对应的类需要【序列化】
- 保证序列化的兼容性
  - private static final long serialVersionID = 1L;