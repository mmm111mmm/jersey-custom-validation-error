package com.newfivefour.jerseycustomvalidationerror

import java.util.{HashSet, Iterator}

import javax.validation.{Path, ConstraintViolation, ConstraintViolationException}
import javax.validation.metadata.ConstraintDescriptor

object CustomValidationError {

  class P(var pathName: String) extends javax.validation.Path {
    def iterator():Iterator[Path.Node] = {
      return new Iterator[Path.Node] {
        def hasNext(): Boolean = false
        def next(): javax.validation.Path.Node = null
      }
    }
    override def toString:String = pathName
  }

  def throwCustomValidationException(obj: Object, argPath: String, message: String, invalid: Object) = {
    var hs = new HashSet[ConstraintViolation[_]]
    hs.add(new ConstraintViolation[Object] {
      def getConstraintDescriptor(): ConstraintDescriptor[_] = null
      def getExecutableParameters(): Array[Object] = null
      def getExecutableReturnValue(): Object = null
      def getInvalidValue(): Object = invalid
      def getLeafBean(): Object = null
      def getMessage(): String = message
      def getMessageTemplate(): String = ""
      def getPropertyPath(): Path = new P(argPath)
      def getRootBean(): Object = obj
      def getRootBeanClass(): Class[Object] = classOf[Object]
      def unwrap[U](t: Class[U]): U = t.newInstance
    })
    throw new ConstraintViolationException("", hs)
  }

}

