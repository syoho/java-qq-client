
- implements Serializable：序列化
  - 一个对象需要通过Object流来读写，对象对应的类需要【序列化】
- 保证序列化的兼容性
  - private static final long serialVersionID = 1L;
- 无参构造器
- Socket
- new Socket(InetAddress.getByName("127.0.0.1"),9999);