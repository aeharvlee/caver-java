/*
 * Copyright 2020 The caver-java Authors
 *
 * Licensed under the Apache License, Version 2.0 (the “License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an “AS IS” BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.klaytn.caver.transaction.type;

import com.klaytn.caver.rpc.Klay;
import com.klaytn.caver.transaction.AbstractFeeDelegatedTransaction;
import com.klaytn.caver.transaction.AbstractFeeDelegatedWithRatioTransaction;
import com.klaytn.caver.transaction.TransactionDecoder;
import com.klaytn.caver.utils.BytesUtils;
import com.klaytn.caver.utils.Utils;
import com.klaytn.caver.wallet.keyring.SignatureData;
import org.web3j.crypto.Hash;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.rlp.*;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a fee delegated value transfer memo with ratio transaction.
 * Please refer to https://docs.klaytn.com/klaytn/design/transactions/partial-fee-delegation#txtypefeedelegatedvaluetransfermemowithratio to see more detail.
 */
public class FeeDelegatedValueTransferMemoWithRatio extends AbstractFeeDelegatedWithRatioTransaction {

    /**
     * The account address that will receive the transferred value.
     */
    String to;

    /**
     * The amount of KLAY in peb to be transferred.
     */
    String value;

    /**
     * Data attached to the transaction. The message should be passed to this attribute.
     */
    String input;

    /**
     * A unit price of gas in peb the sender will pay for a transaction fee.
     */
    String gasPrice = "0x";

    /**
     * FeeDelegatedValueTransferMemo Builder class
     */
    public static class Builder extends AbstractFeeDelegatedWithRatioTransaction.Builder<FeeDelegatedValueTransferMemoWithRatio.Builder> {
        String to;
        String value;
        String input;
        String gasPrice = "0x";

        public Builder() {
            super(TransactionType.TxTypeFeeDelegatedValueTransferMemoWithRatio.toString());
        }

        public Builder setTo(String to) {
            this.to = to;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public Builder setValue(BigInteger value) {
            return setValue(Numeric.toHexStringWithPrefix(value));
        }

        public Builder setInput(String input) {
            this.input = input;
            return this;
        }

        public Builder setGasPrice(String gasPrice) {
            this.gasPrice = gasPrice;
            return this;
        }

        public Builder setGasPrice(BigInteger gasPrice) {
            setGasPrice(Numeric.toHexStringWithPrefix(gasPrice));
            return this;
        }

        public FeeDelegatedValueTransferMemoWithRatio build() {
            return new FeeDelegatedValueTransferMemoWithRatio(this);
        }
    }

    /**
     * Creates a FeeDelegatedValueTransferMemoWithRatio instance.
     * @param builder FeeDelegatedValueTransferMemoWithRatio.Builder instance.
     * @return FeeDelegatedValueTransferMemoWithRatio
     */
    public static FeeDelegatedValueTransferMemoWithRatio create(FeeDelegatedValueTransferMemoWithRatio.Builder builder) {
        return new FeeDelegatedValueTransferMemoWithRatio(builder);
    }

    /**
     * Creates a FeeDelegatedValueTransferMemoWithRatio instance.
     * @param klaytnCall Klay RPC instance
     * @param from The address of the sender.
     * @param nonce A value used to uniquely identify a sender’s transaction.
     * @param gas The maximum amount of gas the transaction is allowed to use.
     * @param chainId Network ID.
     * @param signatures A sender signature list.
     * @param feePayer A fee payer address.
     * @param feePayerSignatures A fee payer signature list.
     * @param feeRatio  A fee ratio of the fee payer.
     * @param to The account adderss that will receive the transferred value.
     * @param value The amount of KLAY in peb to be transferred.
     * @param input The message data attached to the transaction.
     * @return FeeDelegatedValueTransferMemoWithRatio
     */
    public static FeeDelegatedValueTransferMemoWithRatio create(Klay klaytnCall, String from, String nonce, String gas, String chainId, List<SignatureData> signatures, String feePayer, List<SignatureData> feePayerSignatures, String feeRatio, String to, String value, String input) {
        return new FeeDelegatedValueTransferMemoWithRatio(klaytnCall, from, nonce, gas, chainId, signatures, feePayer, feePayerSignatures, feeRatio, to, value, input);
    }

    /**
     * Creates a FeeDelegatedValueTransferMemoWithRatio instance.
     * @param builder FeeDelegatedValueTransferMemoWithRatio.Builder instance.
     */
    public FeeDelegatedValueTransferMemoWithRatio(FeeDelegatedValueTransferMemoWithRatio.Builder builder) {
        super(builder);
        setTo(builder.to);
        setValue(builder.value);
        setInput(builder.input);
    }

    /**
     * Creates a FeeDelegatedValueTransferMemoWithRatio instance.
     * @param klaytnCall Klay RPC instance
     * @param from The address of the sender.
     * @param nonce A value used to uniquely identify a sender’s transaction.
     * @param gas The maximum amount of gas the transaction is allowed to use.
     * @param chainId Network ID.
     * @param signatures A sender signature list.
     * @param feePayer A fee payer address.
     * @param feePayerSignatures A fee payer signature list.
     * @param feeRatio  A fee ratio of the fee payer.
     * @param to The account adderss that will receive the transferred value.
     * @param value The amount of KLAY in peb to be transferred.
     * @param input The message data attached to the transaction.
     */
    public FeeDelegatedValueTransferMemoWithRatio(Klay klaytnCall, String from, String nonce, String gas, String chainId, List<SignatureData> signatures, String feePayer, List<SignatureData> feePayerSignatures, String feeRatio, String to, String value, String input) {
        super(
                klaytnCall,
                TransactionType.TxTypeFeeDelegatedValueTransferMemoWithRatio.toString(),
                from,
                nonce,
                gas,
                chainId,
                signatures,
                feePayer,
                feePayerSignatures,
                feeRatio
        );
        setTo(to);
        setValue(value);
        setInput(input);
    }

    /**
     * Getter function for gas price
     * @return String
     */
    public String getGasPrice() {
        return gasPrice;
    }

    /**
     * Setter function for gas price.
     * @param gasPrice A unit price of gas in peb the sender will pay for a transaction fee.
     */
    public void setGasPrice(String gasPrice) {
        if(gasPrice == null || gasPrice.isEmpty() || gasPrice.equals("0x")) {
            gasPrice = "0x";
        }

        if(!gasPrice.equals("0x") && !Utils.isNumber(gasPrice)) {
            throw new IllegalArgumentException("Invalid gasPrice. : " + gasPrice);
        }

        this.gasPrice = gasPrice;
    }

    /**
     * Setter function for gas price.
     * @param gasPrice A unit price of gas in peb the sender will pay for a transaction fee.
     */
    public void setGasPrice(BigInteger gasPrice) {
        setGasPrice(Numeric.toHexStringWithPrefix(gasPrice));
    }

    /**
     * Decodes a RLP-encoded FeeDelegatedValueTransferMemo string.
     * @param rlpEncoded RLP-encoded FeeDelegatedValueTransferMemo string.
     * @return FeeDelegatedValueTransferMemo
     */
    public static FeeDelegatedValueTransferMemoWithRatio decode(String rlpEncoded) {
        return decode(Numeric.hexStringToByteArray(rlpEncoded));
    }

    /**
     * Decodes a RLP-encoded FeeDelegatedValueTransferMemo byte array.
     * @param rlpEncoded RLP-encoded FeeDelegatedValueTransferMemo byte array.
     * @return FeeDelegatedValueTransferMemo
     */
    public static FeeDelegatedValueTransferMemoWithRatio decode(byte[] rlpEncoded) {
        //TxHashRLP = type + encode([nonce, gasPrice, gas, to, value, from, input, feeRatio, txSignatures, feePayer, feePayerSignatures])
        if(rlpEncoded[0] != (byte)TransactionType.TxTypeFeeDelegatedValueTransferMemoWithRatio.getType()) {
            throw new IllegalArgumentException("Invalid RLP-encoded tag - " + TransactionType.TxTypeFeeDelegatedValueTransferMemoWithRatio.toString());
        }

        //remove Tag
        byte[] detachedType = Arrays.copyOfRange(rlpEncoded, 1, rlpEncoded.length);

        RlpList rlpList = RlpDecoder.decode(detachedType);
        List<RlpType> values = ((RlpList) rlpList.getValues().get(0)).getValues();

        BigInteger nonce = ((RlpString) values.get(0)).asPositiveBigInteger();
        BigInteger gasPrice = ((RlpString) values.get(1)).asPositiveBigInteger();
        BigInteger gas = ((RlpString) values.get(2)).asPositiveBigInteger();
        String to = ((RlpString) values.get(3)).asString();
        BigInteger value = ((RlpString) values.get(4)).asPositiveBigInteger();
        String from = ((RlpString) values.get(5)).asString();
        String input = ((RlpString) values.get(6)).asString();
        BigInteger feeRatio = ((RlpString) values.get(7)).asPositiveBigInteger();

        List<RlpType> senderSignatures = ((RlpList) (values.get(8))).getValues();
        List<SignatureData> senderSignList = SignatureData.decodeSignatures(senderSignatures);

        String feePayer = ((RlpString) values.get(9)).asString();

        List<RlpType> feePayerSignatures = ((RlpList) (values.get(10))).getValues();
        List<SignatureData> feePayerSignList = SignatureData.decodeSignatures(feePayerSignatures);

        FeeDelegatedValueTransferMemoWithRatio feeDelegatedValueTransferMemoWithRatio = new FeeDelegatedValueTransferMemoWithRatio.Builder()
                .setNonce(nonce)
                .setGasPrice(gasPrice)
                .setGas(gas)
                .setTo(to)
                .setValue(value)
                .setFrom(from)
                .setInput(input)
                .setSignatures(senderSignList)
                .setFeeRatio(feeRatio)
                .setFeePayer(feePayer)
                .setFeePayerSignatures(feePayerSignList)
                .build();

        return feeDelegatedValueTransferMemoWithRatio;
    }

    /**
     * Returns the RLP-encoded string of this transaction (i.e., rawTransaction).
     * @return String
     */
    @Override
    public String getRLPEncoding() {
        //TxHashRLP = type + encode([nonce, gasPrice, gas, to, value, from, input, feeRatio, txSignatures, feePayer, feePayerSignatures])
        this .validateOptionalValues(false);

        List<RlpType> senderSignatureRLPList = new ArrayList<>();
        List<RlpType> feePayerSignatureRLPList = new ArrayList<>();

        for(SignatureData signatureData : this.getSignatures()) {
            senderSignatureRLPList.add(signatureData.toRlpList());
        }

        for(SignatureData signatureData : this.getFeePayerSignatures()) {
            feePayerSignatureRLPList.add(signatureData.toRlpList());
        }

        List<RlpType> rlpTypeList = new ArrayList<>();
        rlpTypeList.add(RlpString.create(Numeric.toBigInt(this.getNonce())));
        rlpTypeList.add(RlpString.create(Numeric.toBigInt(this.getGasPrice())));
        rlpTypeList.add(RlpString.create(Numeric.toBigInt(this.getGas())));
        rlpTypeList.add(RlpString.create(Numeric.hexStringToByteArray(this.getTo())));
        rlpTypeList.add(RlpString.create(Numeric.toBigInt(this.getValue())));
        rlpTypeList.add(RlpString.create(Numeric.hexStringToByteArray(this.getFrom())));
        rlpTypeList.add(RlpString.create(Numeric.hexStringToByteArray(this.getInput())));
        rlpTypeList.add(RlpString.create(Numeric.toBigInt(this.getFeeRatio())));
        rlpTypeList.add(new RlpList(senderSignatureRLPList));
        rlpTypeList.add(RlpString.create(Numeric.hexStringToByteArray(this.getFeePayer())));
        rlpTypeList.add(new RlpList(feePayerSignatureRLPList));

        byte[] encodedTransaction = RlpEncoder.encode(new RlpList(rlpTypeList));
        byte[] type = new byte[] { (byte)TransactionType.TxTypeFeeDelegatedValueTransferMemoWithRatio.getType() };
        byte[] rawTx = BytesUtils.concat(type, encodedTransaction);

        return Numeric.toHexString(rawTx);
    }

    /**
     * Returns the RLP-encoded string to make the signature of this transaction.
     * @return String
     */
    @Override
    public String getCommonRLPEncodingForSignature() {
        //SigFeePayerRLP = encode([encode([type, nonce, gasPrice, gas, to, value, from, input, feeRatio]), feePayer, chainid, 0, 0])
        //encode([type, nonce, gasPrice, gas, to, value, from, input, feeRatio])
        this.validateOptionalValues(true);

        byte type = (byte)TransactionType.TxTypeFeeDelegatedValueTransferMemoWithRatio.getType();

        List<RlpType> rlpTypeList = new ArrayList<>();
        rlpTypeList.add(RlpString.create(type));
        rlpTypeList.add(RlpString.create(Numeric.toBigInt(this.getNonce())));
        rlpTypeList.add(RlpString.create(Numeric.toBigInt(this.getGasPrice())));
        rlpTypeList.add(RlpString.create(Numeric.toBigInt(this.getGas())));
        rlpTypeList.add(RlpString.create(Numeric.hexStringToByteArray(this.getTo())));
        rlpTypeList.add(RlpString.create(Numeric.toBigInt(this.getValue())));
        rlpTypeList.add(RlpString.create(Numeric.hexStringToByteArray(this.getFrom())));
        rlpTypeList.add(RlpString.create(Numeric.hexStringToByteArray(this.getInput())));
        rlpTypeList.add(RlpString.create(Numeric.toBigInt(this.getFeeRatio())));

        byte[] encoded = RlpEncoder.encode(new RlpList(rlpTypeList));
        String encodedStr = Numeric.toHexString(encoded);

        return encodedStr;
    }

    /**
     * Returns a senderTxHash of transaction
     * @return String
     */
    @Override
    public String getSenderTxHash() {
        //SenderTxHashRLP = type + encode([nonce, gasPrice, gas, to, value, from, input, feeRatio, txSignatures])
        //SenderTxHash = keccak256(SenderTxHashRLP)
        this.validateOptionalValues(false);

        List<RlpType> senderSignatureRLPList = new ArrayList<>();

        for(SignatureData signatureData : this.getSignatures()) {
            senderSignatureRLPList.add(signatureData.toRlpList());
        }

        List<RlpType> rlpTypeList = new ArrayList<>();
        rlpTypeList.add(RlpString.create(Numeric.toBigInt(this.getNonce())));
        rlpTypeList.add(RlpString.create(Numeric.toBigInt(this.getGasPrice())));
        rlpTypeList.add(RlpString.create(Numeric.toBigInt(this.getGas())));
        rlpTypeList.add(RlpString.create(Numeric.hexStringToByteArray(this.getTo())));
        rlpTypeList.add(RlpString.create(Numeric.toBigInt(this.getValue())));
        rlpTypeList.add(RlpString.create(Numeric.hexStringToByteArray(this.getFrom())));
        rlpTypeList.add(RlpString.create(Numeric.hexStringToByteArray(this.getInput())));
        rlpTypeList.add(RlpString.create(Numeric.toBigInt(this.getFeeRatio())));
        rlpTypeList.add(new RlpList(senderSignatureRLPList));

        byte[] encodedTransaction = RlpEncoder.encode(new RlpList(rlpTypeList));
        byte[] type = new byte[] { (byte)TransactionType.TxTypeFeeDelegatedValueTransferMemoWithRatio.getType() };
        byte[] rawTx = BytesUtils.concat(type, encodedTransaction);

        return Numeric.toHexString(Hash.sha3(rawTx));
    }

    /**
     * Checks that member variables that can be defined by the user are defined.
     * If there is an undefined variable, an error occurs.
     */
    @Override
    public void validateOptionalValues(boolean checkChainID) {
        super.validateOptionalValues(checkChainID);
        if(this.getGasPrice() == null || this.getGasPrice().isEmpty() || this.getGasPrice().equals("0x")) {
            throw new RuntimeException("gasPrice is undefined. Define gasPrice in transaction or use 'transaction.fillTransaction' to fill values.");
        }
    }

    /**
     * Check equals txObj passed parameter and Current instance.
     * @param txObj The AbstractFeeDelegatedWithRatioTransaction Object to compare
     * @param checkSig Check whether signatures field is equal.
     * @return boolean
     */
    @Override
    public boolean compareTxField(AbstractFeeDelegatedWithRatioTransaction txObj, boolean checkSig) {
        if(!super.compareTxField(txObj, checkSig)) return false;
        if(!(txObj instanceof FeeDelegatedValueTransferMemoWithRatio)) return false;
        FeeDelegatedValueTransferMemoWithRatio feeDelegatedValueTransferMemoWithRatio = (FeeDelegatedValueTransferMemoWithRatio)txObj;

        if(!this.getTo().toLowerCase().equals(feeDelegatedValueTransferMemoWithRatio.getTo().toLowerCase())) return false;
        if(!Numeric.toBigInt(this.getValue()).equals(Numeric.toBigInt(feeDelegatedValueTransferMemoWithRatio.getValue()))) return false;
        if(!this.getInput().equals(feeDelegatedValueTransferMemoWithRatio.getInput())) return false;
        if(!this.getGasPrice().equals(feeDelegatedValueTransferMemoWithRatio.getGasPrice())) return false;

        return true;
    }

    @Override
    public void fillTransaction() throws IOException {
        Klay klaytnCall = this.getKlaytnCall();
        if(klaytnCall != null) {
            if(this.getNonce().equals("0x")) {
                this.setNonce(klaytnCall.getTransactionCount(this.getFrom(), DefaultBlockParameterName.PENDING).send().getResult());
            }

            if(this.getChainId().equals("0x")) {
                this.setChainId(klaytnCall.getChainID().send().getResult());
            }

            if(this.gasPrice.equals("0x")) {
                this.setGasPrice(klaytnCall.getGasPrice().send().getResult());
            }

        }

        if(this.getNonce().equals("0x") || this.getChainId().equals("0x") || this.getGasPrice().equals("0x")) {
            throw new RuntimeException("Cannot fill transaction data.(nonce, chainId, gasPrice). `klaytnCall` must be set in Transaction instance to automatically fill the nonce, chainId or gasPrice. Please call the `setKlaytnCall` to set `klaytnCall` in the Transaction instance.");
        }
    }

    @Override
    public String combineSignedRawTransactions(List<String> rlpEncoded) {
        boolean fillVariable = false;

        // If the signatures are empty, there may be an undefined member variable.
        // In this case, the empty information is filled with the decoded result.
        // At initial state of AbstractFeeDelegateTx Object, feePayerSignature field has one empty signature.
        if((Utils.isEmptySig(this.getFeePayerSignatures())) || Utils.isEmptySig(this.getSignatures())) fillVariable = true;

        for(String encodedStr : rlpEncoded) {
            AbstractFeeDelegatedTransaction decode = (AbstractFeeDelegatedTransaction) TransactionDecoder.decode(encodedStr);
            if (!decode.getType().equals(this.getType())) {
                continue;
            }
            FeeDelegatedValueTransferMemo txObj = (FeeDelegatedValueTransferMemo) decode;

            if(fillVariable) {
                if(this.getNonce().equals("0x")) this.setNonce(txObj.getNonce());
                if(this.getGasPrice().equals("0x")) this.setGasPrice(txObj.getGasPrice());
                if(this.getFeePayer().equals("0x") || this.getFeePayer().equals(Utils.DEFAULT_ZERO_ADDRESS)) {
                    if(!txObj.getFeePayer().equals("0x") && !txObj.getFeePayer().equals(Utils.DEFAULT_ZERO_ADDRESS)) {
                        this.setFeePayer(txObj.getFeePayer());
                        fillVariable = false;
                    }
                }
            }

            // Signatures can only be combined for the same transaction.
            // Therefore, compare whether the decoded transaction is the same as this.
            if(!this.compareTxField(txObj, false)) {
                throw new RuntimeException("Transactions containing different information cannot be combined.");
            }

            this.appendSignatures(txObj.getSignatures());
            this.appendFeePayerSignatures(txObj.getFeePayerSignatures());
        }

        return this.getRLPEncoding();
    }

    /**
     * Getter function for to
     * @return String
     */
    public String getTo() {
        return to;
    }

    /**
     * Getter function for value
     * @return String
     */
    public String getValue() {
        return value;
    }

    /**
     * Getter function for input
     * @return String
     */
    public String getInput() {
        return input;
    }

    /**
     * Setter function for to
     * @param to The account address that will receive the transferred value.
     */
    public void setTo(String to) {
        if(to == null) {
            throw new IllegalArgumentException("to is missing.");
        }

        if(!Utils.isAddress(to)) {
            throw new IllegalArgumentException("Invalid address. : " + to);
        }
        this.to = to;
    }

    /**
     * Setter function for value
     * @param value The amount of KLAY in peb to be transferred.
     */
    public void setValue(String value) {
        if(value == null) {
            throw new IllegalArgumentException("value is missing.");
        }

        if(!Utils.isNumber(value)) {
            throw new IllegalArgumentException("Invalid value : " + value);
        }
        this.value = value;
    }

    /**
     * Setter function for value
     * @param value The amount of KLAY in peb to be transferred.
     */
    public void setValue(BigInteger value) {
        setValue(Numeric.toHexStringWithPrefix(value));
    }

    /**
     * Setter function for input
     * @param input Data attached to the transaction. The message should be passed to this attribute.
     */
    public void setInput(String input) {
        if(input == null) {
            throw new IllegalArgumentException("input is missing.");
        }

        if(!Utils.isHex(input)) {
            throw new IllegalArgumentException("Invalid input : " + input);
        }

        this.input = Numeric.prependHexPrefix(input);
    }
}
