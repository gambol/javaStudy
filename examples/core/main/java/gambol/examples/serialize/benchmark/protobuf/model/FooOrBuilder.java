// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: bench.proto

package gambol.examples.serialize.benchmark.protobuf.model;

public interface FooOrBuilder extends
    // @@protoc_insertion_point(interface_extends:benchmark.Foo)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>optional .benchmark.Type type = 1;</code>
   */
  boolean hasType();
  /**
   * <code>optional .benchmark.Type type = 1;</code>
   */
  Type getType();

  /**
   * <code>optional bool flag = 2;</code>
   */
  boolean hasFlag();
  /**
   * <code>optional bool flag = 2;</code>
   */
  boolean getFlag();

  /**
   * <code>optional int32 num32 = 3;</code>
   */
  boolean hasNum32();
  /**
   * <code>optional int32 num32 = 3;</code>
   */
  int getNum32();

  /**
   * <code>optional int64 num64 = 4;</code>
   */
  boolean hasNum64();
  /**
   * <code>optional int64 num64 = 4;</code>
   */
  long getNum64();

  /**
   * <code>optional string str = 5;</code>
   */
  boolean hasStr();
  /**
   * <code>optional string str = 5;</code>
   */
  String getStr();
  /**
   * <code>optional string str = 5;</code>
   */
  com.google.protobuf.ByteString
      getStrBytes();

  /**
   * <code>repeated .benchmark.Foo children = 10;</code>
   */
  java.util.List<Foo>
      getChildrenList();
  /**
   * <code>repeated .benchmark.Foo children = 10;</code>
   */
  Foo getChildren(int index);
  /**
   * <code>repeated .benchmark.Foo children = 10;</code>
   */
  int getChildrenCount();
  /**
   * <code>repeated .benchmark.Foo children = 10;</code>
   */
  java.util.List<? extends FooOrBuilder>
      getChildrenOrBuilderList();
  /**
   * <code>repeated .benchmark.Foo children = 10;</code>
   */
  FooOrBuilder getChildrenOrBuilder(
          int index);
}