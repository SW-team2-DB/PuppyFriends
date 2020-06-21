/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.sw.PuppyFriends;
// Declare any non-default types here with import statements

public interface IMyAidlInterface extends android.os.IInterface
{
  /** Default implementation for IMyAidlInterface. */
  public static class Default implements com.sw.PuppyFriends.IMyAidlInterface
  {
    @Override public int getCount() throws android.os.RemoteException
    {
      return 0;
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements com.sw.PuppyFriends.IMyAidlInterface
  {
    private static final java.lang.String DESCRIPTOR = "com.sw.PuppyFriends.IMyAidlInterface";
    /** Construct the stub at attach it to the interface. */
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an com.sw.PuppyFriends.IMyAidlInterface interface,
     * generating a proxy if needed.
     */
    public static com.sw.PuppyFriends.IMyAidlInterface asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof com.sw.PuppyFriends.IMyAidlInterface))) {
        return ((com.sw.PuppyFriends.IMyAidlInterface)iin);
      }
      return new com.sw.PuppyFriends.IMyAidlInterface.Stub.Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      java.lang.String descriptor = DESCRIPTOR;
      switch (code)
      {
        case INTERFACE_TRANSACTION:
        {
          reply.writeString(descriptor);
          return true;
        }
        case TRANSACTION_getCount:
        {
          data.enforceInterface(descriptor);
          int _result = this.getCount();
          reply.writeNoException();
          reply.writeInt(_result);
          return true;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
    }
    private static class Proxy implements com.sw.PuppyFriends.IMyAidlInterface
    {
      private android.os.IBinder mRemote;
      Proxy(android.os.IBinder remote)
      {
        mRemote = remote;
      }
      @Override public android.os.IBinder asBinder()
      {
        return mRemote;
      }
      public java.lang.String getInterfaceDescriptor()
      {
        return DESCRIPTOR;
      }
      @Override public int getCount() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        int _result;
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getCount, _data, _reply, 0);
          if (!_status && getDefaultImpl() != null) {
            return getDefaultImpl().getCount();
          }
          _reply.readException();
          _result = _reply.readInt();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
        return _result;
      }
      public static com.sw.PuppyFriends.IMyAidlInterface sDefaultImpl;
    }
    static final int TRANSACTION_getCount = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    public static boolean setDefaultImpl(com.sw.PuppyFriends.IMyAidlInterface impl) {
      if (Stub.Proxy.sDefaultImpl == null && impl != null) {
        Stub.Proxy.sDefaultImpl = impl;
        return true;
      }
      return false;
    }
    public static com.sw.PuppyFriends.IMyAidlInterface getDefaultImpl() {
      return Stub.Proxy.sDefaultImpl;
    }
  }
  public int getCount() throws android.os.RemoteException;
}
